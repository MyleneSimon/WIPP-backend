package gov.nist.itl.ssd.wipp.backend.data.genericdatacollection;

import gov.nist.itl.ssd.wipp.backend.core.CoreConfig;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.ClientException;
import gov.nist.itl.ssd.wipp.backend.data.genericdatacollection.genericfiles.GenericFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Service class for local backend import
 @author Mylene Simon <mylene.simon at nist.gov>
 **/
@Service
public class GenericDataCollectionLocalImporter {

    @Autowired
    CoreConfig config;

    @Autowired
    private GenericDataCollectionRepository genericDataCollectionRepository;

    @Autowired
    private GenericFileHandler genericFileHandler;

    protected void importFromLocalFolder(GenericDataCollection genericDataCollection) {
        try {
            File localImportFolder = new File(config.getLocalImportFolder(), genericDataCollection.getSourceBackendImport());

            // Register files in collection
            genericFileHandler.importFolderCopy(genericDataCollection.getId(), localImportFolder);

        } catch (IOException ex) {
            throw new ClientException("Error while importing data.");
        }
    }
}
