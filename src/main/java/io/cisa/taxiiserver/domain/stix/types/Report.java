package io.cisa.taxiiserver.domain.stix.types;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;

public class Report extends StixObject{
	@Field("name")
	private String name;
	
	@Field("description")
	private String description;
	
	@Field("published")
	private ZonedDateTime published;
	
	@Field("object_refs")
	@JsonProperty("object_refs")
	private Set<String> objectRefs = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ZonedDateTime getPublished() {
		return published;
	}

	public void setPublished(ZonedDateTime published) {
		this.published = published;
	}

	public Set<String> getObjectRefs() {
		return objectRefs;
	}

	public void setObjectRefs(Set<String> objectRefs) {
		this.objectRefs = objectRefs;
	}
	
	
}
