package io.cisa.taxiiserver.domain.stix.types;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;

public class Relationship extends StixObject{
	@Field("relationship_type")
	@JsonProperty("relationship_type")
	private String relationshipType;
	
	@Field("description")
	private String description;
	
	@Field("source_ref")
	@JsonProperty("source_ref")
	private String sourceRef;
	
	@Field("target_ref")
	@JsonProperty("target_ref")
	private String targetRef;

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}
	
	
}
