package gov.nist.itl.ssd.wipp.backend.app;

import gov.nist.itl.ssd.wipp.backend.core.model.computation.Plugin;
import gov.nist.itl.ssd.wipp.backend.core.model.computation.PluginRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.Workflow;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowCopyService;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowStatus;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.ImagesCollection;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.ImagesCollectionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WippTools {

    @Autowired
    private PluginRepository pluginRepository;

    @Autowired
    private ImagesCollectionRepository imagesCollectionRepository;

    @Autowired
    private WorkflowCopyService workflowCopyService;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Tool(description = "List the plugins that are available")
    public List<Plugin> listPlugins() {
        return pluginRepository.findAll();
    }

    @Tool(description = "Find all images collections")
    public Page<ImagesCollection> findAllImagesCollections() {
        return imagesCollectionRepository.findAll(Pageable.ofSize(10));
    }

    @Tool(description = "Copy a workflow (identified by workflowId) into a new one. " +
    "A name (workflowName) must be provided for the new workflow, " +
            "a description (workflowDescription) is optional")
    public Workflow copyWorkflow(String workflowId, String workflowName, String workflowDescription) {
        String copyWorkflowOwner = SecurityContextHolder.getContext().getAuthentication().getName();
        return workflowCopyService.copy(workflowId, workflowName, workflowDescription, copyWorkflowOwner,
                WorkflowStatus.CREATED);
    }

    @Tool(description = "Find workflows by name.")
    public Page<Workflow> findWorkflowByName(String workflowName) {
        return workflowRepository.findByNameContainingIgnoreCase(workflowName, Pageable.ofSize(10));
    }

//    @Tool(description = "Create new images collection")
}
