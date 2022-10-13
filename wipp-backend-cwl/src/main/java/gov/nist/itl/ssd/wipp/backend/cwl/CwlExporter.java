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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import gov.nist.itl.ssd.wipp.backend.argo.workflows.plugin.Plugin;
import gov.nist.itl.ssd.wipp.backend.argo.workflows.plugin.PluginIO;
import gov.nist.itl.ssd.wipp.backend.argo.workflows.plugin.PluginRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.data.DataHandler;
import gov.nist.itl.ssd.wipp.backend.core.model.data.DataHandlerService;
import gov.nist.itl.ssd.wipp.backend.core.model.data.DefaultDataHandler;
import gov.nist.itl.ssd.wipp.backend.core.model.job.Job;
import gov.nist.itl.ssd.wipp.backend.core.model.job.JobRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.Workflow;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3id.cwl.cwl.*;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts WIPP workflow configuration to CWL workflow
 *
 * @author Mylene Simon <mylene.simon at nist.gov>
 */
@Component
public class CwlExporter {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PluginRepository wippPluginRepository;

    @Autowired
    private DataHandlerService dataHandlerService;

    public String exportWorkflowToCwl(Workflow workflow) throws Exception {
        org.w3id.cwl.cwl.Workflow cwlWorkflow = new WorkflowImpl();
        cwlWorkflow.setCwlVersion(Optional.of(CWLVersion.V1_2));
        cwlWorkflow.setClass_(Workflow_class.WORKFLOW);

        // Build the list of jobs and plugins
        List<Job> jobList = jobRepository.findByWippWorkflow(workflow.getId());
        Map<String, Plugin> jobsPlugins = new HashMap<>();
        jobList.forEach(job -> {
            Optional<Plugin> plugin = wippPluginRepository.findById(job.getWippExecutable());
            if(!plugin.isPresent()) {
                throw new ClientException("Error while converting workflow: unknown plugin " + job.getWippExecutable());
            }
            jobsPlugins.put(job.getId(), plugin.get());
        });

        List<Object> workflowSteps = new ArrayList<>();
        List<Object> workflowInputs = new ArrayList<>();
        List<Object> workflowOutputs = new ArrayList<>();

        for(Job job: jobList) {
            // Initialize WorkflowStep
            WorkflowStep wfStep = new WorkflowStepImpl();
            wfStep.setId(Optional.of(job.getName()));

            Plugin plugin = jobsPlugins.get(job.getId());

            // Convert WIPP Plugin to CWL CommandLineTool and set as runnable for step
            wfStep.setRun(this.wippPluginToCwlCommandLineTool(plugin));

            // Convert job inputs to step and workflow inputs
            List<Object> wfStepInputs = new ArrayList<>();
            job.getParameters().forEach((paramName, paramValue) -> {
                // Initialize step input
                WorkflowStepInput wfStepInput = new WorkflowStepInputImpl();
                wfStepInput.setId(Optional.of(paramName));
                // Get input details from plugin manifest
                PluginIO paramDetails = this.getParameterDetailsFromPlugin(plugin, paramName);
                // If the input is not a primitive type, check if it is the output of another job
                // and link, otherwise add to list of workflow inputs
                String chainedOutputRegex = "\\{\\{ (.*)\\.(.*) \\}\\}";
                Pattern pattern = Pattern.compile(chainedOutputRegex);
                Matcher m = pattern.matcher(paramValue);
                if(!this.isPrimitiveType(paramDetails.getType()) && m.find()) {
                    String sourceJobId = m.group(1);
                    String sourceOutputName = m.group(2);
                    Optional<Job> sourceJob = this.jobRepository.findById(sourceJobId);
                    if (sourceJob.isPresent()) {
                        Job sourceJobToAdd = (Job) sourceJob.get();
                        wfStepInput.setSource(sourceJobToAdd.getName() + "/" + sourceOutputName);
                    } else {
                        throw new ClientException("Error while converting workflow: unknown chained job with Id "
                                + sourceJobId);
                    }
                } else {
                    WorkflowInputParameter workflowInputParam = new WorkflowInputParameterImpl();
                    workflowInputParam.setId(Optional.of(job.getName() + "-" + paramName));
                    workflowInputParam.setType(this.convertWippTypeToCwlType(paramDetails.getType()));
                    workflowInputs.add(workflowInputParam);
                    wfStepInput.setSource(workflowInputParam.getId());
                }
                wfStepInputs.add(wfStepInput);
            });
            wfStep.setIn(wfStepInputs);

            // Convert job outputs to step and workflow outputs
            List<Object> wfStepOutputs = new ArrayList<>();
            job.getOutputParameters().forEach((paramName, paramValue) -> {
                // Create step and workflow outputs
                WorkflowStepOutput wfStepOutput = new WorkflowStepOutputImpl();
                wfStepOutput.setId(Optional.of(paramName));
                wfStepOutputs.add(wfStepOutput);
                WorkflowOutputParameter workflowOutputParameter = new WorkflowOutputParameterImpl();
                workflowOutputParameter.setId(Optional.of(job.getName() + "-" + paramName));
                workflowOutputParameter.setType(CWLType.DIRECTORY);
                workflowOutputParameter.setOutputSource(job.getName() + "/" + paramName);
                workflowOutputs.add(workflowOutputParameter);
            });
            wfStep.setOut(wfStepOutputs);

            // Add step to list of steps
            workflowSteps.add(wfStep);
        }

        cwlWorkflow.setInputs(workflowInputs);
        cwlWorkflow.setOutputs(workflowOutputs);
        cwlWorkflow.setSteps(workflowSteps);

        YAMLFactory yamlFactory = new YAMLFactory();
        ObjectMapper mapper = new ObjectMapper(yamlFactory);
        // Register Jdk8Module to deal with Optional type
        mapper.registerModule(new Jdk8Module());
        // Don't serialize null fields, write enums values
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);

        return mapper.writeValueAsString(cwlWorkflow);

    }

    protected CommandLineTool wippPluginToCwlCommandLineTool(Plugin plugin) {

        // Initialize CommandLineTool
        CommandLineTool commandLineTool = new CommandLineToolImpl();
        commandLineTool.setClass_(CommandLineTool_class.COMMANDLINETOOL);
        commandLineTool.setId(Optional.of(plugin.getName()));

        // Set baseCommand if any
        commandLineTool.setBaseCommand(Optional.ofNullable(plugin.getBaseCommand()));

        // Set Docker requirements (plugin container image information)
        List<Object> requirements = new ArrayList<>();
        DockerRequirement dockerRequirement = new DockerRequirementImpl();
        dockerRequirement.setClass_(DockerRequirement_class.DOCKERREQUIREMENT);
        dockerRequirement.setDockerImageId(Optional.of(plugin.getContainerId()));
        requirements.add(dockerRequirement);
        commandLineTool.setRequirements(Optional.of(requirements));

        // Set inputs
        List<Object> commandLineInputs = new ArrayList<>();
        plugin.getInputs().forEach(input -> {
            CommandInputParameter commandInputParameter = new CommandInputParameterImpl();
            commandInputParameter.setType(this.convertWippTypeToCwlType(input.getType()));
            commandInputParameter.setId(Optional.of(input.getName()));
            commandInputParameter.setLabel(Optional.of(input.getDescription()));
            CommandLineBinding cmdLineBinding = new CommandLineBindingImpl();
            cmdLineBinding.setPrefix(Optional.of("--" + input.getName()));
            commandInputParameter.setInputBinding(Optional.of(cmdLineBinding));
            commandLineInputs.add(commandInputParameter);
        });
        commandLineTool.setInputs(commandLineInputs);

        // Set outputs
        List<Object> commandLineOutputs = new ArrayList<>();
        List<Object> commandLineArguments = new ArrayList<>();
        plugin.getOutputs().forEach(output -> {
            CommandOutputParameter commandOutputParameter = new CommandOutputParameterImpl();
            commandOutputParameter.setType(this.convertWippTypeToCwlType(output.getType()));
            commandOutputParameter.setId(Optional.of(output.getName()));
            commandOutputParameter.setLabel(Optional.of(output.getDescription()));
            commandLineOutputs.add(commandOutputParameter);
            commandLineArguments.add("--" + output.getName());
            commandLineArguments.add("$(runtime.outdir)");
        });
        commandLineTool.setOutputs(commandLineOutputs);
        commandLineTool.setArguments(Optional.of(commandLineArguments));

        return commandLineTool;
    }

    private CWLType convertWippTypeToCwlType(String wippDataType) {
        CWLType paramType;
        // Primitive types are passed as-is to CWL type, custom types are converted to Directory
        if (this.isPrimitiveType(wippDataType)) {
            if (wippDataType.equals("number")) {
                paramType = CWLType.DOUBLE;
            } else if (wippDataType.equals("array") || wippDataType.equals("enum")) {
                paramType = CWLType.STRING;
            } else {
                paramType = CWLType.fromDocumentVal(wippDataType);
            }
        } else {
            paramType = CWLType.DIRECTORY;
        }
        return paramType;
    }

    private boolean isPrimitiveType(String dataType) {
        DataHandler dataHandler = dataHandlerService.getDataHandler(dataType);
        // Primitive types are handled by the default data handler
        if (dataHandler instanceof DefaultDataHandler) {
            return true;
        } else {
            return false;
        }
    }

    private PluginIO getParameterDetailsFromPlugin(Plugin plugin, String paramName) {
        final Optional<PluginIO> paramDetails = plugin.getInputs().stream().filter(p -> {
            return p.getName().equals(paramName);
        }).findFirst();
        return paramDetails.get();
    }

    private Optional<Job> getSourceJobOfInput(List<String> jobDependencies, String inputId) {
        final Optional<String> sourceJobId = jobDependencies.stream().filter(j -> {
            Optional<Job> job = this.jobRepository.findById(j);
            if (job.isPresent()) {
                Job matchingJob = (Job) job.get();
                return matchingJob.getOutputParameters().containsValue(inputId);
            } else {
                return false;
            }
        }).findFirst();
        if (sourceJobId.isPresent()){
            return jobRepository.findById(sourceJobId.get());
        } else {
            return Optional.empty();
        }
    }
}
