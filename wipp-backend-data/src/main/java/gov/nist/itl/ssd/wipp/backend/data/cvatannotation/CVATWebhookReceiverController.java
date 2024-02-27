package gov.nist.itl.ssd.wipp.backend.data.cvatannotation;

import gov.nist.itl.ssd.wipp.backend.core.CoreConfig;
import gov.nist.itl.ssd.wipp.backend.data.cvatannotation.cvatmodels.WebhookPayload;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.ImagesCollection;
import gov.nist.itl.ssd.wipp.backend.data.imagescollection.ImagesCollectionRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Arrays.stream;

/**
 *
 * @author Mylene Simon <mylene.simon at nist.gov>
 */

@RestController
@RequestMapping(CoreConfig.BASE_URI + "/cvatWebhookReceiver")
public class CVATWebhookReceiverController {
	
	@Autowired
	CoreConfig config;

	@Autowired
	CVATDatasetAnnotationsRepository cvatDatasetAnnotationsRepository;

	private static final Logger LOGGER = Logger.getLogger(CVATWebhookReceiverController.class.getName());

	@RequestMapping(value = "", method = RequestMethod.POST)
//	@PreAuthorize("isAuthenticated() and "
//    		+ "(hasRole('admin') or @imagesCollectionSecurity.checkAuthorize(#imagesCollectionId, true))")
	public HttpStatus receiveWebhook(
			@RequestBody WebhookPayload payload) throws IOException, InterruptedException {

		LOGGER.info("Received CVAT Webhook event");
		if (payload != null && payload.getTask()!=null) {
			if (payload.getEvent().equals("update:task")) {
				LOGGER.info("CVAT task update event received " + payload.getTask().toString());
				String taskId = String.valueOf(payload.getTask().getId());
				LOGGER.info("Task ID: " + taskId);
				List<CVATDatasetAnnotations> cvatDatasetAnnotationsList = cvatDatasetAnnotationsRepository.findAll();
				CVATDatasetAnnotations cvatDatasetAnnotations = cvatDatasetAnnotationsList.stream()
						.filter(annot -> taskId.equals(annot.getTask_id()))
						.findAny()
						.orElse(null);
				String collId;
				if (cvatDatasetAnnotations != null)
					collId = cvatDatasetAnnotations.getImagesCollection();
				else
					return HttpStatus.NOT_FOUND;

				// Call Annotation API
				List<String> builderCommands = new ArrayList<>();
				builderCommands.add("python3");
				builderCommands.add("/opt/WIPP-Annotation/WIPPAnnotator.py");
				builderCommands.add("--datatype");
				builderCommands.add("coll");
				builderCommands.add("--collID");
				builderCommands.add(collId);
				builderCommands.add("--backendtype");
				builderCommands.add("CVAT");
				builderCommands.add("--labels");
				builderCommands.add("object");
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
			}
		}

		return HttpStatus.OK;
	}


}
