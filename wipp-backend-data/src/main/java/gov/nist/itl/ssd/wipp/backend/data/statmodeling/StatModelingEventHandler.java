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
package gov.nist.itl.ssd.wipp.backend.data.statmodeling;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import gov.nist.itl.ssd.wipp.backend.core.rest.exception.ClientException;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.NotFoundException;

/**
 *
 * @author Mylene Simon <mylene.simon at nist.gov>
 */
@Component
@RepositoryEventHandler(StatModeling.class)
public class StatModelingEventHandler {
	
	@Autowired
	StatModelingRepository statModelingRepository;

	@PreAuthorize("isAuthenticated()")
    @HandleBeforeCreate
    public void handleBeforeCreate(StatModeling statModeling) {
		// Assert statModeling name is unique
		statModeling.setCreationDate(new Date());
        
        // Set the owner to the connected user
		statModeling.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
    }
	
	@HandleBeforeSave
    @PreAuthorize("isAuthenticated() and (hasRole('admin') or #statModeling.owner == principal.name)")
    public void handleBeforeSave(StatModeling statModeling) {
    	// Assert statModeling exists
        Optional<StatModeling> result = statModelingRepository.findById(
        		statModeling.getId());
    	if (!result.isPresent()) {
        	throw new NotFoundException("Statistical Modeling with id " + statModeling.getId() + " not found");
        }

    	StatModeling oldStatModeling = result.get();

    	// A public statModeling cannot become private
    	if (oldStatModeling.isPubliclyShared() && !statModeling.isPubliclyShared()){
            throw new ClientException("Can not change a public statisical modeling to private.");
        }
    	
    	// Owner cannot be changed
        if (!Objects.equals(
        		statModeling.getOwner(),
                oldStatModeling.getOwner())) {
            throw new ClientException("Can not change owner.");
        }

    	// Creation date cannot be changed
        if (!Objects.equals(
        		statModeling.getCreationDate(),
                oldStatModeling.getCreationDate())) {
            throw new ClientException("Can not change creation date.");
        }
    }


}
