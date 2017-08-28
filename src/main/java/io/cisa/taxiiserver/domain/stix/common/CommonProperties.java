package io.cisa.taxiiserver.domain.stix.common;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class CommonProperties {
		
	@Field("created_by_ref")
	@JsonProperty("created_by_ref")
	private String createdByRef;
	
	@Field("created")
	private ZonedDateTime created;
	
	@Field("modified")
	private ZonedDateTime modified;
	
	@Field("revoked")
	private boolean revoked;
	
	@Field("labels")
	private Set<String> labels = new HashSet<>();
	
	@Field("identifier")
	
	private String identifier; // Synonymous id but can be duplicated
	
	@Field("external_references")
	@JsonProperty("external_references")
	private Set<ExternalReference> externalReferences = new HashSet<>();
	
	@Field("object_marking_references")
	@JsonProperty("object_marking_references")
	private Set<MarkingDefinition> objectMarkingReferences = new HashSet<>();
	
	@Field("granular_markings")
	@JsonProperty("granular_markings")
	private Set<GranularMarkingType> granularMarkings = new HashSet<>();

	public abstract void setType(String type);

	public String getCreatedByRef() {
		return createdByRef;
	}

	public void setCreatedByRef(String createdByRef) {
		this.createdByRef = createdByRef;
	}

	public ZonedDateTime getCreated() {
		return created;
	}

	public void setCreated(ZonedDateTime created) {
		this.created = created;
	}

	public ZonedDateTime getModified() {
		return modified;
	}

	public void setModified(ZonedDateTime modified) {
		this.modified = modified;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public Set<String> getLabels() {
		return labels;
	}

	public void setLabels(Set<String> labels) {
		this.labels = labels;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Set<ExternalReference> getExternalReferences() {
		return externalReferences;
	}

	public void setExternalReferences(Set<ExternalReference> externalReferences) {
		this.externalReferences = externalReferences;
	}

	public Set<MarkingDefinition> getObjectMarkingReferences() {
		return objectMarkingReferences;
	}

	public void setObjectMarkingReferences(Set<MarkingDefinition> objectMarkingReferences) {
		this.objectMarkingReferences = objectMarkingReferences;
	}

	public Set<GranularMarkingType> getGranularMarkings() {
		return granularMarkings;
	}

	public void setGranularMarkings(Set<GranularMarkingType> granularMarkings) {
		this.granularMarkings = granularMarkings;
	}
	
	
	
}
