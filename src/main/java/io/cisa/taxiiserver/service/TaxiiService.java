package io.cisa.taxiiserver.service;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import io.cisa.taxiiserver.config.Constants;
import io.cisa.taxiiserver.domain.status.Status;
import io.cisa.taxiiserver.domain.status.StatusFailure;
import io.cisa.taxiiserver.domain.status.StatusSuccess;
import io.cisa.taxiiserver.domain.stix.StixBundle;
import io.cisa.taxiiserver.domain.stix.StixObject;
import io.cisa.taxiiserver.repository.StatusRepository;
import io.cisa.taxiiserver.repository.StixObjectRepository;

@Service
public class TaxiiService {

	private final StixObjectRepository stixObjectRepository;
	private final StatusRepository statusRepository;
	
	public TaxiiService(StixObjectRepository stixObjectRepository, StatusRepository statusRepository) {
		this.stixObjectRepository = stixObjectRepository;
		this.statusRepository = statusRepository;
	}
	
	public StixBundle loadBundle(Page<StixObject> stixPage) {
		StixBundle bundle = new StixBundle();
		bundle.getObjects().addAll(stixPage.getContent());
		return bundle;
	}

	public Status save(StixBundle stixBundle, Status status) {
		stixBundle.getObjects().stream().forEach(so -> {
			StixObject sob = stixObjectRepository.save(so);
			if(Optional.ofNullable(sob).isPresent()) {
				status.getPendings().remove(so.getIdentifier());
				StatusSuccess ss = new StatusSuccess();
				ss.setUrl("");
				status.getSuccesses().add(ss);
				save(status);
			}
		});		
		return status;
	}

	public boolean validate(StixBundle stixBundle, Status status) {
		boolean state = true;
		if(stixBundle.getSpecVersion().isEmpty()) {
			StatusFailure failure = new StatusFailure();		
			failure.setMessage("Stix Bundle Spec is empty");
			status.getFailures().add(failure);
			state = false;
		}
		
		if(stixBundle.getType().isEmpty()) {
			StatusFailure failure = new StatusFailure();
			failure.setMessage("Stix Bundle Type is empty");
			status.getFailures().add(failure);
			state = false;
		}
		
		if(stixBundle.getObjects().size() == 0) {
			StatusFailure failure = new StatusFailure();
			failure.setMessage("Stix Bundle has an empty Objects list");
			status.getFailures().add(failure);
			state = false;
		}
		
		if(!stixBundle.getSpecVersion().equals(Constants.SPEC_VERSION)) {
			StatusFailure failure = new StatusFailure();
			failure.setMessage("Stix Bundle has an unsupported version! (" + stixBundle.getSpecVersion()+") found.");
			status.getFailures().add(failure);
			state = false;
		}
		
		if(!stixBundle.getType().equals(Constants.BUNDLE_TYPE)) {
			StatusFailure failure = new StatusFailure();
			failure.setMessage("Stix Bundle has an empty Objects list");
			status.getFailures().add(failure);
			state = false;
		}
		
		if(state == true) {
			stixBundle.getObjects().stream().map(StixObject::getIdentifier).forEach(status.getPendings()::add);				
		}
		
		save(status);
		
		return state;
	}

	public Status save(Status status) {
		status = statusRepository.save(status);
		return status;
	}
}
