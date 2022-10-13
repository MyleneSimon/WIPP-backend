/*
 * This software was developed at the National Institute of Standards and
 * Technology by employees of the Federal Government in the course of
 * their official duties. Pursuant to title 17 Section 105 of the United
 * States Code this software is not subject to copyright protection and is
 * in the public domain. This software is an experimental system. NIST assumes
 * no responsibility whatsoever for its use by other parties, and makes no
 * guarantees, expressed or implied, about its quality, reliability, or
 * any other characteristic. We would appreciate acknowledgement if the
 * software is used.
 */
package gov.nist.itl.ssd.wipp.backend.cwl;

import gov.nist.itl.ssd.wipp.backend.argo.workflows.plugin.Plugin;
import gov.nist.itl.ssd.wipp.backend.argo.workflows.plugin.PluginIO;
import gov.nist.itl.ssd.wipp.backend.argo.workflows.plugin.PluginRepository;
import gov.nist.itl.ssd.wipp.backend.core.CoreConfig;
import gov.nist.itl.ssd.wipp.backend.core.model.job.Job;
import gov.nist.itl.ssd.wipp.backend.core.model.job.JobRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.job.JobStatus;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.Workflow;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3id.cwl.cwl.*;
import org.w3id.cwl.cwl.utils.RootLoader;

import java.io.File;

import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

/**
 * Converts CWL workflow to WIPP workflow
 *
 * @author Mylene Simon <mylene.simon at nist.gov>
 */
@Component
public class CwlImporter {

    @Autowired
    CoreConfig config;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PluginRepository wippPluginRepository;

    private final EnumSet<CWLType> acceptedCWLPrimitiveTypes = EnumSet.of(
            CWLType.DOUBLE,
            CWLType.BOOLEAN,
            CWLType.FLOAT,
            CWLType.INT,
            CWLType.LONG,
            CWLType.STRING);

    public Workflow importWorkflowFromCwl(Workflow wippWorkflow, String cwlWorkflowDescription) throws Exception {
        final String workflowId = wippWorkflow.getId();
        org.w3id.cwl.cwl.Workflow cwlWorkflow = (org.w3id.cwl.cwl.Workflow) RootLoader.loadDocument(cwlWorkflowDescription);
        Map <String, String> jobIdsMapping = new HashMap<>();
        Map <String, List<String>> jobDependenciesMapping = new HashMap<>();
        cwlWorkflow.getSteps().forEach(step -> {
            WorkflowStep wfStep = (WorkflowStepImpl) step;
            CommandLineTool cmdLineTool = (CommandLineTool) wfStep.getRun();
            // Convert CommandLineTool to WIPP Plugin and register plugin
            Plugin wippPlugin = this.cwlCommandLineToolToWippPlugin(cmdLineTool);
            wippPlugin = wippPluginRepository.save(wippPlugin);
            Job wippJob = new Job();
            wippJob.setName(StringUtils.substringAfterLast(wfStep.getId().get(),"#"));
            wippJob.setWippWorkflow(workflowId);
            wippJob.setWippExecutable(wippPlugin.getId());
            List<String> jobDependencies = new ArrayList<>();
            // Set job inputs
            Map<String, String> inputParams = new HashMap<>();
            wfStep.getIn().forEach(stepInput -> {
                WorkflowStepInput wfStepInput = (WorkflowStepInputImpl) stepInput;
                String value = StringUtils.substringAfterLast((String) wfStepInput.getSource(), "#");
                // If param value contains "/", it is a reference to another step output
                String wippParamValue;
                if(value.contains("/")) {
                    String[] previousStepOutput = value.split("/");
                    wippParamValue = "{{ " + jobIdsMapping.get(previousStepOutput[0]) + "." + previousStepOutput[1] + " }}";
                    jobDependencies.add(previousStepOutput[0]);
                } else {
                    wippParamValue = value;
                }
                inputParams.put(StringUtils.substringAfterLast(wfStepInput.getId().get(), "/"), wippParamValue);
            });
            wippJob.setParameters(inputParams);
            // Set job outputs
            Map<String, String> outputParams = new HashMap<>();
            wfStep.getOut().forEach(stepOutput -> {
                WorkflowStepOutput wfStepOutput = (WorkflowStepOutputImpl) stepOutput;
                outputParams.put(StringUtils.substringAfterLast(wfStepOutput.getId().get(), "/"), null);
            });
            wippJob.setOutputParameters(outputParams);
            // Set job metadata end save
            wippJob.setWippVersion(config.getWippVersion());
            wippJob.setStatus(JobStatus.CREATED);
            wippJob.setError(null);
            wippJob.setCreationDate(new Date());
            wippJob.setOwner(wippWorkflow.getOwner());
            wippJob = (Job) jobRepository.save(wippJob);
            jobIdsMapping.put(wippJob.getName(), wippJob.getId());
            jobDependenciesMapping.put(wippJob.getName(), jobDependencies);
        });
        // Once all jobs have been created, set dependencies
        jobDependenciesMapping.forEach((jobName, jobList) -> {
            Job job = (Job) jobRepository.findById(jobIdsMapping.get(jobName)).get();
            List<String> dependencies = new ArrayList<>();
            jobList.forEach(previousJobName -> {
                dependencies.add(jobIdsMapping.get(previousJobName));
            });
            job.setDependencies(dependencies);
            jobRepository.save(job);
        });
        return wippWorkflow;
    }

    protected Plugin cwlCommandLineToolToWippPlugin(CommandLineTool cmdLineTool) {
        // Initialize Plugin
        Plugin plugin = new Plugin();
        plugin.setName("CWLImport/" + StringUtils.substringAfterLast(cmdLineTool.getId().get(), "/"));
        plugin.setTitle(plugin.getName());
        plugin.setVersion("v" + UUID.randomUUID().toString());

        // Set Plugin container image information
        List<Object> requirements = cmdLineTool.getRequirements().orElse(new ArrayList<>());
        requirements.forEach(requirement -> {
            if(requirement instanceof DockerRequirementImpl) {
                DockerRequirement dockerRequirement = (DockerRequirementImpl) requirement;
                if(dockerRequirement.getDockerImageId() != null && dockerRequirement.getDockerImageId().isPresent()) {
                    plugin.setContainerId(dockerRequirement.getDockerImageId().get());
                } else if (dockerRequirement.getDockerPull() != null && dockerRequirement.getDockerPull().isPresent()) {
                    plugin.setContainerId(dockerRequirement.getDockerPull().get());
                }
                else {
                    throw new RuntimeException("Unable to import CommandLineTool "
                            + cmdLineTool.getId()
                            + ", unsupported Docker requirements");
                }
            }
        });

        // Set baseCommand if any
        List<String> pluginBaseCommand = new ArrayList<>();
        Object cmdLineToolBaseCommand = cmdLineTool.getBaseCommand();
        if (cmdLineToolBaseCommand != null) {
            if (cmdLineToolBaseCommand instanceof String) {
                pluginBaseCommand.add((String) cmdLineToolBaseCommand);
            }
            if (cmdLineToolBaseCommand instanceof List) {
                pluginBaseCommand.addAll((List<String>) cmdLineToolBaseCommand);
            }
        }
        if (!pluginBaseCommand.isEmpty()) {
            plugin.setBaseCommand(pluginBaseCommand);
        }

        // Initialize list of plugin UI definitions
        List<Object> pluginUI = new ArrayList<>();

        // Set inputs
        List<PluginIO> pluginInputs = new ArrayList<>();
        cmdLineTool.getInputs().forEach(input -> {
            CommandInputParameter cmdInputParam = (CommandInputParameterImpl) input;
            // Only CommandLineInput with `inputBinding` appear on the command line
            if (cmdInputParam.getInputBinding() != null && cmdInputParam.getInputBinding().isPresent()) {
                CommandLineBinding cmdLineBinding = cmdInputParam.getInputBinding().get();
                // Prefix defines the parameter name in the command line
                if (cmdLineBinding.getPrefix() != null && cmdLineBinding.getPrefix().isPresent()) {
                    String cmdLinePrefix = cmdLineBinding.getPrefix().get();
                    if (!cmdLinePrefix.startsWith("--")) {
                        throw new RuntimeException("Unable to import CommandLineTool "
                                + cmdLineTool.getId().get()
                                + ", input prefix format not supported");
                    } else if (!cmdLinePrefix.matches("^--[a-zA-Z0-9][-a-zA-Z0-9]*$")) {
                        throw new RuntimeException("Unable to import CommandLineTool "
                                + cmdLineTool.getId().get()
                                + ", input prefix contains unsupported characters");
                    } else {
                        PluginIO pluginInput = new PluginIO();
                        Map<String, String> pluginInputUI = new HashMap<>();
                        // WIPP input name is `inputBindind-prefix` without "--"
                        pluginInput.setName(cmdLinePrefix.substring(2));
                        CWLType inputType = (CWLType) cmdInputParam.getType();
                        // CWL Directory type is treated as WIPP collection
                        if (CWLType.DIRECTORY.equals(inputType)) {
                            pluginInput.setType("collection");
                            pluginInputUI.put("description", "Pick a collection...");
                        // CWL primitive types are similar to WIPP primitive types
                        } else if (inputType != null && this.acceptedCWLPrimitiveTypes.contains(inputType)) {
                            pluginInput.setType(inputType.toString());
                        // All other CWL types are not supported
                        } else {
                            throw new RuntimeException("Unable to import CommandLineTool "
                                    + cmdLineTool.getId()
                                    + ", input type not supported");
                        }
                        // Set input description if any
                        pluginInput.setDescription(cmdInputParam.getLabel().orElse(null));
                        // Set input UI definition
                        pluginInputUI.put("key", "inputs." + pluginInput.getName());
                        if (pluginInput.getDescription() != null && !pluginInput.getDescription().isEmpty()) {
                            pluginInputUI.put("title", pluginInput.getDescription());
                        } else {
                            pluginInputUI.put("title", pluginInput.getName());
                        }
                        pluginUI.add(pluginInputUI);
                        // Register input
                        pluginInputs.add(pluginInput);
                    }

                } else {
                    throw new RuntimeException("Unable to import CommandLineTool "
                            + cmdLineTool.getId()
                            + ", input binding not supported");
                }
            }
        });
        plugin.setInputs(pluginInputs);
        plugin.setUi(pluginUI);

        // Set outputs
        List<PluginIO> pluginOutputs = new ArrayList<>();
        cmdLineTool.getOutputs().forEach(output -> {
            CommandOutputParameter cmdOutputParam = (CommandOutputParameterImpl) output;
            PluginIO pluginOutput = new PluginIO();
            // Get output type
            CWLType outputType = (CWLType) cmdOutputParam.getType();
            // CWL Directory type is treated as WIPP collection and is the only supported type for outputs
            if (CWLType.DIRECTORY.equals(outputType)) {
                pluginOutput.setType("collection");
            } else {
                throw new RuntimeException("Unable to import this CommandLineTool, " +
                        "output type not supported");
            }
            // Set output name from output id
            pluginOutput.setName(StringUtils.substringAfterLast(cmdOutputParam.getId().get(),"/"));
            // Set output description if any
            pluginOutput.setDescription(cmdOutputParam.getLabel().orElse(pluginOutput.getName()));
            // Register output
            pluginOutputs.add(pluginOutput);
        });
        plugin.setOutputs(pluginOutputs);

        return plugin;
    }
}
