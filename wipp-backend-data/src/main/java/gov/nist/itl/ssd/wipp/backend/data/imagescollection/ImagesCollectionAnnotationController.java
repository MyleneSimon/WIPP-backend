package gov.nist.itl.ssd.wipp.backend.data.imagescollection;

import gov.nist.itl.ssd.wipp.backend.core.CoreConfig;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.ClientException;
import gov.nist.itl.ssd.wipp.backend.data.cvatannotation.CVATDatasetAnnotations;
import gov.nist.itl.ssd.wipp.backend.data.cvatannotation.CVATDatasetAnnotationsRepository;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.ImagesCollection.ImagesCollectionImportMethod;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.files.FileHandler;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.images.Image;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.images.ImageConversionService;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.images.ImageHandler;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.images.ImageRepository;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.metadatafiles.MetadataFileHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 *
 * @author Mylene Simon <mylene.simon at nist.gov>
 */

@RestController
@Tag(name="ImagesCollection Entity")
@RequestMapping(CoreConfig.BASE_URI + "/imagesCollections/{imagesCollectionId}/annotate")
public class ImagesCollectionAnnotationController {
	
	@Autowired
	CoreConfig config;

	@Autowired
	private ImagesCollectionRepository imagesCollectionRepository;

	@Autowired
	CVATDatasetAnnotationsRepository cvatDatasetAnnotationsRepository;

	private static final Logger LOGGER = Logger.getLogger(ImagesCollectionAnnotationController.class.getName());

	@RequestMapping(value = "", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated() and "
    		+ "(hasRole('admin') or @imagesCollectionSecurity.checkAuthorize(#imagesCollectionId, true))")
	public CVATDatasetAnnotations importFromCatalog(
			@PathVariable("imagesCollectionId") String imagesCollectionId,
			@RequestBody String labels) throws IOException, InterruptedException {

		Optional<ImagesCollection> tc = imagesCollectionRepository.findById(imagesCollectionId);

		if (!tc.isPresent()) {
			throw new ResourceNotFoundException(
					"Images collection " + imagesCollectionId + " not found.");
		}

		ImagesCollection imagesCollection = tc.get();

		// Call Annotation API
		List<String> builderCommands = new ArrayList<>();
		builderCommands.add("python3");
		builderCommands.add("/opt/WIPP-Annotation/WIPPAnnotator.py");
		builderCommands.add("--datatype");
		builderCommands.add("coll");
		builderCommands.add("--collID");
		builderCommands.add(imagesCollectionId);
		builderCommands.add("--backendtype");
		builderCommands.add("CVAT");
		builderCommands.add("--labels");
		builderCommands.add(labels);
		builderCommands.add("--username");
		builderCommands.add("wipp");
		builderCommands.add("--password");
		builderCommands.add("dummypass");
		builderCommands.add("--dburl");
		builderCommands.add("mongodb://wipp-mongodb:27017");

		LOGGER.info(builderCommands.toString());

		ProcessBuilder builder = new ProcessBuilder(builderCommands);
		builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
		Process process;

		// Create CVAT task
		process = builder.start();
		int exitCode = process.waitFor();

		// if exit code is zero, execution was successful
		if (exitCode == 0) {
			InputStream is = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = reader.readLine();
			if(line != null){
				LOGGER.info(line);
			}
			// else execution failed, get error message
		} else {
			InputStream es = process.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(es));
			String errorMsg = errorReader.readLine();
			LOGGER.info(errorMsg);
			throw new RuntimeException(errorMsg);
		}

		return cvatDatasetAnnotationsRepository.findByImagesCollection(imagesCollectionId);
	}


}
