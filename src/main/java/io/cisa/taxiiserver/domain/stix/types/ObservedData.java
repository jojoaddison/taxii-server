package io.cisa.taxiiserver.domain.stix.types;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;

public class ObservedData extends StixObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8902498786898920481L;

	@Field("first_observed")
	@JsonProperty("first_observed")
	private ZonedDateTime firstObserved;
	
	@Field("last_observed")
	@JsonProperty("last_observed")
	private ZonedDateTime lastObserved;
	
	@Field("number_observed")
	@JsonProperty("number_observed")
	private int numberObserved;
	
	@Field("objects")
	private Set<Object> objects = new HashSet<>();

	public ZonedDateTime getFirstObserved() {
		return firstObserved;
	}

	public void setFirstObserved(ZonedDateTime firstObserved) {
		this.firstObserved = firstObserved;
	}

	public ZonedDateTime getLastObserved() {
		return lastObserved;
	}

	public void setLastObserved(ZonedDateTime lastObserved) {
		this.lastObserved = lastObserved;
	}

	public int getNumberObserved() {
		return numberObserved;
	}

	public void setNumberObserved(int numberObserved) {
		this.numberObserved = numberObserved;
	}

	public Set<Object> getObjects() {
		return objects;
	}

	public void setObjects(Set<Object> objects) {
		this.objects = objects;
	}
	
	
	
}
