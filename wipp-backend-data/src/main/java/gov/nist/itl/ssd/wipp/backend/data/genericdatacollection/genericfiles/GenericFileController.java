package gov.nist.itl.ssd.wipp.backend.data.genericdatacollection.genericfiles;

import gov.nist.itl.ssd.wipp.backend.core.CoreConfig;
import gov.nist.itl.ssd.wipp.backend.core.model.data.DataDownloadToken;
import gov.nist.itl.ssd.wipp.backend.core.model.data.DataDownloadTokenRepository;
import gov.nist.itl.ssd.wipp.backend.core.rest.DownloadUrl;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.ClientException;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.ForbiddenException;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.NotFoundException;
import gov.nist.itl.ssd.wipp.backend.data.genericdatacollection.GenericDataCollection;
import gov.nist.itl.ssd.wipp.backend.data.genericdatacollection.GenericDataCollectionRepository;

import gov.nist.itl.ssd.wipp.backend.data.imagescollection.images.ImageController;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
*
* @author Mohamed Ouladi <mohamed.ouladi at labshare.org>
*/
@RestController
@Tag(name="GenericDataCollection Entity")
@RequestMapping(CoreConfig.BASE_URI + "/genericDataCollections/{genericDataCollectionId}/genericFile")
@ExposesResourceFor(GenericFile.class)
public class GenericFileController {
	
	@Autowired
    private EntityLinks entityLinks;

    @Autowired
    private GenericFileRepository genericFileRepository;

    @Autowired
    private GenericDataCollectionRepository genericDataCollectionRepository;

    @Autowired
    private GenericFileHandler genericFileHandler;

    @Autowired
    private DataDownloadTokenRepository dataDownloadTokenRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasRole('admin') or @genericDataCollectionSecurity.checkAuthorize(#genericDataCollectionId, false)")
    public HttpEntity<PagedModel<EntityModel<GenericFile>>> getFilesPage(
            @PathVariable("genericDataCollectionId") String genericDataCollectionId,
            @ParameterObject @PageableDefault Pageable pageable,
            @Parameter(hidden = true) PagedResourcesAssembler<GenericFile> assembler) {
        Page<GenericFile> files = genericFileRepository.findByGenericDataCollection(
        		genericDataCollectionId, pageable);
        PagedModel<EntityModel<GenericFile>> resources
                = assembler.toModel(files);
        resources.forEach(
                resource -> processResource(genericDataCollectionId, resource));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated() and "
    		+ "(hasRole('admin') or @genericDataCollectionSecurity.checkAuthorize(#genericDataCollectionId, true))")
    public void deleteAllFiles(
            @PathVariable("genericDataCollectionId") String genericDataCollectionId) {
        Optional<GenericDataCollection> tc = genericDataCollectionRepository.findById(
        		genericDataCollectionId);
        if (!tc.isPresent()) {
            throw new NotFoundException("Collection not found");
        }
        if (tc.get().isLocked()) {
            throw new ClientException("Collection locked.");
        }
        genericFileHandler.deleteAll(genericDataCollectionId);
    }

    @RequestMapping(value = "/{fileName:.+}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated() and "
    		+ "(hasRole('admin') or @genericDataCollectionSecurity.checkAuthorize(#genericDataCollectionId, true))")
    public void deleteFile(
            @PathVariable("genericDataCollectionId") String genericDataCollectionId,
            @PathVariable("fileName") String fileName) {
        Optional<GenericDataCollection> tc = genericDataCollectionRepository.findById(
        		genericDataCollectionId);
        if (!tc.isPresent()) {
            throw new NotFoundException("Collection not found");
        }
        if (tc.get().isLocked()) {
            throw new ClientException("Collection locked.");
        }
        genericFileHandler.delete(genericDataCollectionId, fileName);
    }

    @RequestMapping(
            value = "/{fileName:.+}/request",
            method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize("hasRole('admin') or @genericDataCollectionSecurity.checkAuthorize(#genericDataCollectionId, false)")
    public DownloadUrl requestImageDownload(
            @PathVariable("genericDataCollectionId") String genericDataCollectionId,
            @PathVariable("fileName") String fileName) {
        // Generate and send unique download URL
        String tokenParam = generateDownloadTokenParam(genericDataCollectionId);
        String genericFilePath = "/" + fileName;
        String downloadLink = linkTo(GenericFileController.class,
                genericDataCollectionId).toString() + genericFilePath + tokenParam;
        return new DownloadUrl(downloadLink);
    }

    @RequestMapping(value = "/{fileName:.+}", method = RequestMethod.HEAD)
    public void headFile(
            @PathVariable("genericDataCollectionId") String genericDataCollectionId,
            @PathVariable("fileName") String fileName,
            @RequestParam("token") String token,
            HttpServletResponse response) throws IOException {
        // Check validity of download token
        checkDownloadTokenValidity(token, genericDataCollectionId);
        // Check existence of file and send length
        File file = genericFileHandler.getFile(genericDataCollectionId, fileName);
        if (!file.exists()) {
            throw new NotFoundException("File does not exist.");
        }
        response.setContentLengthLong(file.length());
    }

    @RequestMapping(value = "/{fileName:.+}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("genericDataCollectionId") String genericDataCollectionId,
            @PathVariable("fileName") String fileName,
            @RequestParam("token") String token,
            HttpServletResponse response) throws IOException {
        // Check validity of download token
        checkDownloadTokenValidity(token, genericDataCollectionId);
        // Send file
        File file = genericFileHandler.getFile(genericDataCollectionId, fileName);
        response.setContentLengthLong(file.length());
        response.setHeader("Content-disposition",
                "attachment;filename=" + fileName);
        try (InputStream fis = new FileInputStream(file)) {
            IOUtils.copyLarge(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException ex) {
            throw new NotFoundException("File does not exist.", ex);
        }
    }

    protected void processResource(String genericDataCollectionId,
                                   EntityModel<GenericFile> resource) {
        GenericFile file = resource.getContent();
        Link link = entityLinks.linkForItemResource(
                GenericDataCollection.class, genericDataCollectionId)
                .slash("genericFile")
                .slash(file.getFileName())
                .withSelfRel();
        resource.add(link);

        link = entityLinks.linkForItemResource(
                        GenericDataCollection.class, genericDataCollectionId)
                .slash("genericFile")
                .slash(file.getFileName())
                .slash("request")
                .withRel("download");
        resource.add(link);
    }

    private void checkDownloadTokenValidity(String token, String genericDataCollectionId) {
        Optional<DataDownloadToken> downloadToken = dataDownloadTokenRepository.findByToken(token);
        if (!downloadToken.isPresent() || !downloadToken.get().getDataId().equals(genericDataCollectionId)) {
            throw new ForbiddenException("Invalid download token.");
        }
    }

    private String generateDownloadTokenParam(String genericDataCollectionId) {
        // Check existence of generic data collection
        Optional<GenericDataCollection> tc = genericDataCollectionRepository.findById(
                genericDataCollectionId);
        if (!tc.isPresent()) {
            throw new ResourceNotFoundException(
                    "Generic Data collection " + genericDataCollectionId + " not found.");
        }

        // Generate download token
        DataDownloadToken downloadToken = new DataDownloadToken(genericDataCollectionId);
        dataDownloadTokenRepository.save(downloadToken);

        // Generate token param
        String tokenParam = "?token=" + downloadToken.getToken();

        return tokenParam;
    }
}
