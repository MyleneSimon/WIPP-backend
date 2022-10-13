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
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.Workflow;
import gov.nist.itl.ssd.wipp.backend.core.model.workflow.WorkflowRepository;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.ClientException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mylene Simon <mylene.simon at nist.gov>
 */
@RestController
@Api(tags="Workflow Entity")
@RequestMapping(CoreConfig.BASE_URI + "/workflows/{workflowId}/cwl")
public class CwlExportController {

    @Autowired
    CoreConfig config;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private CwlExporter cwlExporter;

    private static final Logger LOGGER = Logger.getLogger(CwlExportController.class.getName());

    @PreAuthorize("isAuthenticated() and @workflowSecurity.checkAuthorize(#workflowId, false)")
    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = { "text/x-yaml;charset=UTF-8" }
    )
    public String export(
            @PathVariable("workflowId") String workflowId,
            HttpServletResponse response
    ) {
        // Retrieve Workflow object
        Optional<Workflow> wippWorkflow = workflowRepository.findById(
                workflowId
        );

        if (!wippWorkflow.isPresent()) {
            throw new ClientException("Received submission of unknown workflow");
        }

        Workflow workflow = wippWorkflow.get();
        String cwlWorkflow;

        response.setHeader("Content-disposition",
                "attachment;filename=" + workflow.getName() + "-cwl.yaml");
        try {
            cwlWorkflow = cwlExporter.exportWorkflowToCwl(workflow);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Cannot export workflow: " + ex.getMessage());
            throw new ClientException("Error while exporting workflow: " + ex.getMessage());
        }
        return cwlWorkflow;
    }
}
