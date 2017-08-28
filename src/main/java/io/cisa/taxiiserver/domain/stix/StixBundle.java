package io.cisa.taxiiserver.domain.stix;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "stixbundles")
public class StixBundle {

	@Id
	private String id;

	@Field("type")
	private String type;

	@Field("spec_version")
	@JsonProperty("spec_version")
	private String specVersion;

	@Field("objects")
	private Set<StixObject> objects = new HashSet<>();
	
	public StixBundle() {
		type = "bundle";
		specVersion = "2.0";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public StixBundle id(String id) {
		this.id = id;
		return this;
	}

	public String getType() {
		return type;
	}


	public String getSpecVersion() {
		return specVersion;
	}


	public Set<StixObject> getObjects() {
		return objects;
	}

	public void setObjects(Set<StixObject> objects) {
		this.objects = objects;
	}

	public StixBundle objects(Set<StixObject> objects) {
		this.objects = objects;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StixBundle stixBundle = (StixBundle) o;
		if (stixBundle.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), stixBundle.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

    @Override
	public String toString() {
		return "StixBundle{" 
				+ "id=" + getId() + ", " 
				+ "type=" + getType() + ", " 
				+ "spec_version=" + getSpecVersion() + ", " 
				+ "objects=" + getObjects() + "}";
	}
}
