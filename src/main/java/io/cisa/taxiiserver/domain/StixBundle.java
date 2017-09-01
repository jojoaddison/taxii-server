package io.cisa.taxiiserver.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stixbundles")
public class StixBundle {

	private String id;

	private String type;

	private String specVersion;

	private Set<StixObject> objects = new HashSet<>();

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

	public void setType(String type) {
		this.type = type;
	}

	public StixBundle type(String type) {
		this.type = type;
		return this;
	}

	public String getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

	public StixBundle specVersion(String specVersion) {
		this.specVersion = specVersion;
		return this;
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
