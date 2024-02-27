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
package gov.nist.itl.ssd.wipp.backend.data.imagescollection.annotationfiles;

import gov.nist.itl.ssd.wipp.backend.core.CoreConfig;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.NotFoundException;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 *
 * @author Mylene Simon <mylene.simon at nist.gov>
 */
@RestController
@Api(tags="ImagesCollection Entity")
@RequestMapping(CoreConfig.BASE_URI + "/imagesCollections/{imagesCollectionId}/annotationFiles/")
public class AnnotationFileController {

    @Autowired
    private CoreConfig config;

    @RequestMapping(value = "/{fileName:.+}", method = RequestMethod.HEAD)
    @PreAuthorize("hasRole('admin') or @imagesCollectionSecurity.checkAuthorize(#imagesCollectionId, false)")
    public void headFile(
            @PathVariable("imagesCollectionId") String imagesCollectionId,
            @PathVariable("fileName") String fileName,
            HttpServletResponse response) throws IOException {
        File file = this.getFile(imagesCollectionId, fileName);
        if (!file.exists()) {
            throw new NotFoundException("File does not exist.");
        }
        response.setContentLengthLong(file.length());
    }

    @RequestMapping(value = "/{fileName:.+}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('admin') or @imagesCollectionSecurity.checkAuthorize(#imagesCollectionId, false)")
    public void getFile(
            @PathVariable("imagesCollectionId") String imagesCollectionId,
            @PathVariable("fileName") String fileName,
            HttpServletResponse response) throws IOException {
        File file = this.getFile(imagesCollectionId, fileName);

        response.setContentLengthLong(file.length());
        try (InputStream fis = new FileInputStream(file)) {
            IOUtils.copyLarge(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException ex) {
            throw new NotFoundException("File does not exist.", ex);
        }
    }

    public File getFilesFolder(String imagesCollectionId) {
        return new File(
                new File(config.getImagesCollectionsFolder(), imagesCollectionId),
                "cvat-annotations");
    }

    public File getFile(String imagesCollectionId, String fileName) {
        return new File(getFilesFolder(imagesCollectionId), fileName);
    }

}
