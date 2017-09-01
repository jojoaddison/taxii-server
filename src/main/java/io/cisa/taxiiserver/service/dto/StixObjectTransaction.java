package io.cisa.taxiiserver.service.dto;

import io.cisa.taxiiserver.domain.status.Status;
import io.cisa.taxiiserver.domain.stix.StixObject;

public class StixObjectTransaction {
	private StixObject stixObject;
	private Status status;
	public StixObjectTransaction(StixObject stixObject, Status status) {
		this.stixObject = stixObject;
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public StixObject getStixObject() {
		return stixObject;
	}
}
