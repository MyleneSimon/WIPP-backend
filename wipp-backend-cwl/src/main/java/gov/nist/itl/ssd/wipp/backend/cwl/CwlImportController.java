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

import gov.nist.itl.ssd.wipp.backend.core.CoreConfig;
import gov.nist.itl.ssd.wipp.backend.core.model.job.JobRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.Workflow;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowLogic;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowRepository;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowStatus;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.ClientException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mylene Simon <mylene.simon at nist.gov>
 */
@Controller
@Api(tags="Workflow Entity")
@RequestMapping(CoreConfig.BASE_URI + "/workflows/cwl-import")
public class CwlImportController {

    @Autowired
    CoreConfig config;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WorkflowLogic workflowLogic;

    @Autowired
    private CwlImporter cwlImporter;

    private static final Logger LOGGER = Logger.getLogger(CwlImportController.class.getName());

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            produces = { "application/json" }
    )
    public ResponseEntity<Workflow> uploadCwlWorkflow(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name
    ) {
        // Workflow name sanity checks
        if (name == null || name.isEmpty()) {
            throw new ClientException(
                    "A workflow name must be specified.");
        }
        workflowLogic.assertWorkflowNameUnique(name);

        // Create new Workflow
        Workflow workflow = new Workflow(name);
        // Set the owner to the connected user
        workflow.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
        // Set status to CREATED and save to generate unique Id
        workflow.setStatus(WorkflowStatus.CREATED);
        workflow = workflowRepository.save(workflow);


        try {
            String cwlContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            cwlImporter.importWorkflowFromCwl(workflow, cwlContent);
            return new ResponseEntity<>(workflow, HttpStatus.OK);
        } catch (Exception ex) {
            jobRepository.deleteByWippWorkflow(workflow.getId());
            workflowRepository.delete(workflow);
            LOGGER.log(Level.SEVERE, "Cannot create workflow: " + ex.getMessage());
            throw new ClientException("Error while importing workflow: " + ex.getMessage());
        }
    }
}
