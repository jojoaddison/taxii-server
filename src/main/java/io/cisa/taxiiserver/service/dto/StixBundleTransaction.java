package io.cisa.taxiiserver.service.dto;

import io.cisa.taxiiserver.domain.status.Status;
import io.cisa.taxiiserver.domain.stix.StixBundle;

public class StixBundleTransaction {
	private StixBundle stixBundle;
	private Status status;
	
	public StixBundleTransaction(StixBundle stixBundle, Status status) {		
		this.stixBundle = stixBundle;
		this.status = status;
	}
	
	public StixBundle getStixBundle() {
		return stixBundle;
	}
	public Status getStatus() {
		return status;
	}
}
