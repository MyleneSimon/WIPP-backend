package gov.nist.itl.ssd.wipp.backend.data.iterativeai.templates;

import gov.nist.itl.ssd.wipp.backend.core.CoreConfig;
import gov.nist.itl.ssd.wipp.backend.core.model.computation.Plugin;
import gov.nist.itl.ssd.wipp.backend.core.model.computation.PluginRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.job.Job;
import gov.nist.itl.ssd.wipp.backend.core.model.job.JobRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.job.JobStatus;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.Workflow;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowCopyService;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowStatus;
import gov.nist.itl.ssd.wipp.backend.data.iterativeai.IterativeTrainingPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WIPPUnetTemplate {

    @Autowired
    CoreConfig config;

    @Autowired
    WorkflowRepository workflowRepository;

    @Autowired
    PluginRepository pluginRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WorkflowCopyService workflowCopyService;

    public Workflow createWorkflow(IterativeTrainingPipeline pipeline,
                                   String iterationName,
                                   IterativeTrainingPipeline.TrainingIteration previousIteration,
                                   int newIterationNumber) {
        Workflow workflow;
        if(newIterationNumber == 1 || previousIteration == null) {
            workflow = new Workflow();
            workflow.setName(iterationName);
            workflow.setCreationDate(new Date());
            workflow.setOwner(pipeline.getOwner());
            workflow.setStatus(WorkflowStatus.PENDING);
            workflow = workflowRepository.save(workflow);

            // Create training job
            Job trainingJob = new Job();
            trainingJob.setName(workflow.getName() + "-training");
            trainingJob.setWippWorkflow(workflow.getId());
            trainingJob.setWippVersion(config.getWippVersion());
            trainingJob.setStatus(JobStatus.CREATED);
            trainingJob.setCreationDate(new Date());
            trainingJob.setOwner(pipeline.getOwner());
            Plugin trainingPlugin = pluginRepository.findOneByNameAndVersion("WIPP UNet CNN Training Plugin", "1.0.0");
            trainingJob.setWippExecutable(trainingPlugin.getId());
            // add params
            Map<String, String> inputParameters = new HashMap<>();
            inputParameters.put("imageDir", pipeline.getTrainingCollection());
            inputParameters.put("maskDir", pipeline.getGroundTruthCollection());
            inputParameters.put("useTiling", "NO");
            inputParameters.put("trainFraction", "0.8");
            inputParameters.put("batchSize", "1");
            inputParameters.put("numberClasses", "2");
            inputParameters.put("learningRate", "3e-4");
            inputParameters.put("testEveryNSteps", "200");
            inputParameters.put("balanceClasses", "YES");
            inputParameters.put("earlyStoppingEpochCount", "5");
            inputParameters.put("useIntensityScaling", "YES");
            inputParameters.put("useAugmentation", "YES");
            inputParameters.put("augmentationReflection", "YES");
            inputParameters.put("augmentationRotation", "YES");
            trainingJob.setParameters(inputParameters);

            Map<String, String> outputParameters = new HashMap<>();
            outputParameters.put("outputDir", null);
            outputParameters.put("tensorboardDir", null);
            trainingJob.setOutputParameters(outputParameters);
            jobRepository.save(trainingJob);

            // Create inference job
            Job inferJob = new Job();
            inferJob.setName(workflow.getName() + "-infer");
            inferJob.setWippWorkflow(workflow.getId());
            inferJob.setWippVersion(config.getWippVersion());
            inferJob.setStatus(JobStatus.CREATED);
            inferJob.setCreationDate(new Date());
            inferJob.setOwner(pipeline.getOwner());
            Plugin inferPlugin = pluginRepository.findOneByNameAndVersion("WIPP UNet CNN Inference Plugin", "1.0.0");
            inferJob.setWippExecutable(inferPlugin.getId());
            List<String> dependencies = new ArrayList<>();
            dependencies.add(trainingJob.getId());
            inferJob.setDependencies(dependencies);
            // add params
            Map<String, String> inferInputParameters = new HashMap<>();
            inferInputParameters.put("imageDir", pipeline.getTrainingCollection());
            inferInputParameters.put("model", "{{ " + trainingJob.getId() + ".outputDir }}");
            inferInputParameters.put("useIntensityScaling", "YES");
            inferJob.setParameters(inferInputParameters);

            Map<String, String> inferOutputParameters = new HashMap<>();
            inferOutputParameters.put("outputDir", null);
            inferJob.setOutputParameters(inferOutputParameters);
            jobRepository.save(inferJob);
        } else {
            // Copy workflow from previous iteration if not first iteration
            workflow = workflowCopyService.copy(previousIteration.getTrainingWorkflow(), iterationName,
                    "Iterative AI pipeline " + pipeline.getName() + " iteration " + newIterationNumber,
                    pipeline.getOwner(), WorkflowStatus.PENDING);
        }
        return workflow;
    }
}
