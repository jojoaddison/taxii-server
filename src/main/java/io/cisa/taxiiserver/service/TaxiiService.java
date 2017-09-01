package io.cisa.taxiiserver.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import io.cisa.taxiiserver.config.Constants;
import io.cisa.taxiiserver.domain.status.Status;
import io.cisa.taxiiserver.domain.status.StatusFailure;
import io.cisa.taxiiserver.domain.status.StatusSuccess;
import io.cisa.taxiiserver.domain.stix.StixBundle;
import io.cisa.taxiiserver.domain.stix.StixObject;
import io.cisa.taxiiserver.repository.StatusRepository;
import io.cisa.taxiiserver.repository.StixBundleRepository;
import io.cisa.taxiiserver.repository.StixObjectRepository;
import io.cisa.taxiiserver.service.dto.StixBundleTransaction;
import io.cisa.taxiiserver.service.dto.StixObjectTransaction;

@Service
public class TaxiiService {

	private final StixObjectRepository stixObjectRepository;
	private final StatusRepository statusRepository;
	private final StixBundleRepository stixBundleRepository;

	public TaxiiService(StixObjectRepository stixObjectRepository, StatusRepository statusRepository,
			StixBundleRepository stixBundleRepository) {
		this.stixObjectRepository = stixObjectRepository;
		this.statusRepository = statusRepository;
		this.stixBundleRepository = stixBundleRepository;
	}

	public StixBundle loadBundle(Page<StixObject> stixPage) {
		StixBundle bundle = new StixBundle();
		bundle.getObjects().addAll(stixPage.getContent());
		return bundle;
	}

	public void process(StixBundle stixBundle, Status status) {
		Thread taxiiRunner = new Thread(
				new StixBundleRunner(stixBundle, status, stixObjectRepository, statusRepository));
		taxiiRunner.start();
	}

	public void process(StixObject stixObject, Status status) {
		Thread taxiiRunner = new Thread(
				new StixObjectRunner(stixObject, status, stixObjectRepository, statusRepository));
		taxiiRunner.start();
	}

	public boolean validate(StixObject stixObject, Status status) {
		
		return false;
	}
	
	@JmsListener(destination="taxiiStixBundleQueue", containerFactory="taxiiStixFactory")
	public void readStixBundles(StixBundleTransaction stixBundleTransaction) {
		StixBundle stixBundle = stixBundleTransaction.getStixBundle();
		Status status = stixBundleTransaction.getStatus();
		if(validate(stixBundle, status)) {
			Set<StixObject> objects = stixBundle.getObjects();
			objects.stream().forEach(so -> {
				save(so, status);
			});		
		}
	}
	

	@JmsListener(destination="taxiiStixObjectQueue", containerFactory="taxiiStixFactory")
	public void readStixObjects(StixObjectTransaction sot) {
		save(sot.getStixObject(), sot.getStatus());
	}

	public boolean validate(StixBundle stixBundle, Status status) {
		boolean state = true;
		if (stixBundle.getSpecVersion().isEmpty()) {
			StatusFailure failure = new StatusFailure();
			failure.setMessage("Stix Bundle Spec is empty");
			status.getFailures().add(failure);
			state = false;
		}

		if (stixBundle.getType().isEmpty()) {
			StatusFailure failure = new StatusFailure();
			failure.setMessage("Stix Bundle Type is empty");
			status.getFailures().add(failure);
			state = false;
		}

		if (stixBundle.getObjects().size() == 0) {
			StatusFailure failure = new StatusFailure();
			failure.setMessage("Stix Bundle has an empty Objects list");
			status.getFailures().add(failure);
			state = false;
		}

		if (!stixBundle.getSpecVersion().equals(Constants.SPEC_VERSION)) {
			StatusFailure failure = new StatusFailure();
			failure.setMessage("Stix Bundle has an unsupported version! (" + stixBundle.getSpecVersion() + ") found.");
			status.getFailures().add(failure);
			state = false;
		}

		if (!stixBundle.getType().equals(Constants.BUNDLE_TYPE)) {
			StatusFailure failure = new StatusFailure();
			failure.setMessage("Stix Bundle has an empty Objects list");
			status.getFailures().add(failure);
			state = false;
		}

		if (state == true) {
			stixBundle.getObjects().stream().map(StixObject::getIdentifier).forEach(status.getPendings()::add);
		}

		save(status);

		return state;
	}

	public Status save(Status status) {
		status = statusRepository.save(status);
		return status;
	}
	
	public void save(StixObject so, Status status) {
		StixObject sob = stixObjectRepository.save(so);
		status.getPendings().remove(so.getIdentifier());
		if (Optional.ofNullable(sob).isPresent()) {
			StatusSuccess ss = new StatusSuccess();
			ss.setId(so.getType().concat("--").concat(sob.getId()));
			ss.setUrl(status.getRequestUrl().concat("/").concat(ss.getId()));
			status.getSuccesses().add(ss);
		} else {
			StatusFailure sf = new StatusFailure();
			sf.setId(so.getIdentifier());
			sf.setMessage("Failed saving stix object");
			status.getFailures().add(sf);
		}
		statusRepository.save(status);
	}

	private class StixBundleRunner implements Runnable {

		private final StixBundle stixBundle;
		private final StixObjectRepository stixObjectRepository;
		private final StatusRepository statusRepository;
		private final Status status;

		public StixBundleRunner(StixBundle stixBundle, Status status, StixObjectRepository stixObjectRepository,
				StatusRepository statusRepository) {
			this.stixBundle = stixBundle;
			this.status = status;
			this.stixObjectRepository = stixObjectRepository;
			this.statusRepository = statusRepository;
		}

		@Override
		public void run() {
			Set<StixObject> objects = stixBundle.getObjects();
			objects.stream().forEach(so -> {
				StixObject sob = stixObjectRepository.save(so);
				status.getPendings().remove(so.getIdentifier());
				if (Optional.ofNullable(sob).isPresent()) {
					StatusSuccess ss = new StatusSuccess();
					ss.setId(so.getType().concat("--").concat(sob.getId()));
					ss.setUrl(status.getRequestUrl().concat("/").concat(ss.getId()));
					status.getSuccesses().add(ss);
				} else {
					StatusFailure sf = new StatusFailure();
					sf.setId(so.getIdentifier());
					sf.setMessage("Failed saving stix object");
					status.getFailures().add(sf);
				}
				statusRepository.save(status);
			});

		}

	}

	private class StixObjectRunner implements Runnable {

		private final StixObject stixObject;
		private final StixObjectRepository stixObjectRepository;
		private final StatusRepository statusRepository;
		private final Status status;

		public StixObjectRunner(StixObject stix, Status status, StixObjectRepository stixObjectRepository,
				StatusRepository statusRepository) {
			this.stixObject = stix;
			this.status = status;
			this.stixObjectRepository = stixObjectRepository;
			this.statusRepository = statusRepository;
		}

		@Override
		public void run() {
			StixObject sob = stixObjectRepository.save(stixObject);
			if (Optional.ofNullable(sob).isPresent()) {
				status.getPendings().remove(sob.getIdentifier());
				StatusSuccess ss = new StatusSuccess();
				ss.setId(sob.getId());
				ss.setUrl(status.getRequestUrl().concat("/").concat(sob.getId()));
				status.getSuccesses().add(ss);
			} else {

			}
			statusRepository.save(status);

		}

	}

}
