package io.cisa.taxiiserver.domain.stix.types;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;

public class Sighting extends StixObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9029135991966520348L;

	@Field("first_seen")
	@JsonProperty("first_seen")
	private ZonedDateTime firstSeen;
	
	@Field("last_seen")
	@JsonProperty("last_seen")
	private ZonedDateTime lastSeen;
	
	@Field("count")
	private int count;
	
	@Field("sighting_of_ref")
	@JsonProperty("sighting_of_ref")
	private String sightingOfRef;
	
	@Field("observed_data_refs")
	@JsonProperty("observed_data_refs")
	private Set<String> observedDataRefs = new HashSet<>();
	
	@Field("where_sighted_refs")
	@JsonProperty("where_sighted_refs")
	private Set<String> whereSightedRefs = new HashSet<>();
	
	@Field("summary")
	private boolean summary;
	
	public Sighting() {
		super();
		setType("sighting");
	}

	public ZonedDateTime getFirstSeen() {
		return firstSeen;
	}

	public void setFirstSeen(ZonedDateTime firstSeen) {
		this.firstSeen = firstSeen;
	}

	public ZonedDateTime getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(ZonedDateTime lastSeen) {
		this.lastSeen = lastSeen;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSightingOfRef() {
		return sightingOfRef;
	}

	public void setSightingOfRef(String sightingOfRef) {
		this.sightingOfRef = sightingOfRef;
	}

	public Set<String> getObservedDataRefs() {
		return observedDataRefs;
	}

	public void setObservedDataRefs(Set<String> observedDataRefs) {
		this.observedDataRefs = observedDataRefs;
	}

	public Set<String> getWhereSightedRefs() {
		return whereSightedRefs;
	}

	public void setWhereSightedRefs(Set<String> whereSightedRefs) {
		this.whereSightedRefs = whereSightedRefs;
	}

	public boolean isSummary() {
		return summary;
	}

	public void setSummary(boolean summary) {
		this.summary = summary;
	}
	
	
	
}
