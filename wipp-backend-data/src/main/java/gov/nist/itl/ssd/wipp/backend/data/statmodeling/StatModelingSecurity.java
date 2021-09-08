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

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import gov.nist.itl.ssd.wipp.backend.core.rest.exception.ForbiddenException;
import gov.nist.itl.ssd.wipp.backend.core.rest.exception.NotFoundException;

/**
 * Statistical Modeling Security service
 * 
 * @author Mylene Simon <mylene.simon at nist.gov>
 *
 */
@Service
public class StatModelingSecurity {
	
	@Autowired
    private StatModelingRepository statModelingRepository;

    public boolean checkAuthorize(String statModelingId, Boolean editMode) {
        Optional<StatModeling> statModeling = statModelingRepository.findById(statModelingId);
        if (statModeling.isPresent()){
            return(checkAuthorize(statModeling.get(), editMode));
        }
        else {
            throw new NotFoundException("Statistical Modeling with id " + statModelingId + " not found");
        }
    }

    public static boolean checkAuthorize(StatModeling statModeling, Boolean editMode) {
        String statModelingOwner = statModeling.getOwner();
        String connectedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!statModeling.isPubliclyShared() && (statModelingOwner == null || !statModelingOwner.equals(connectedUser))) {
            throw new ForbiddenException("You do not have access to this Statistical Modeling");
        }
        if (statModeling.isPubliclyShared() && editMode && (statModelingOwner == null || !statModelingOwner.equals(connectedUser))){
            throw new ForbiddenException("You do not have the right to edit this Statistical Modeling");
        }
        return(true);
    }

}
