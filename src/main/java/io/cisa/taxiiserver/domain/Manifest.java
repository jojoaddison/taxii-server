package io.cisa.taxiiserver.domain;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

public class Manifest {
	
	@Field("url")
	private String url;
	
	@Field("versions")
	private Set<String> versions = new HashSet<>();
	
	@Field("date_added")
	private ZonedDateTime dateAdded;
	
	@Field("media_types")
	private Set<String> mediaTypes = new HashSet<>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Manifest url(String url) {
		this.url = url;
		return this;
	}

	public Set<String> getVersions() {
		return versions;
	}

	public void setVersions(Set<String> versions) {
		this.versions = versions;
	}	

	public Manifest versions(Set<String> versions) {
		this.versions = versions;
		return this;
	}

	public ZonedDateTime getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(ZonedDateTime dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	public Manifest dateAdded(ZonedDateTime dateAdded) {
		this.dateAdded = dateAdded;
		return this;
	}

	public Set<String> getMediaTypes() {
		return mediaTypes;
	}

	public void setMediaTypes(Set<String> mediaTypes) {
		this.mediaTypes = mediaTypes;
	}

	public Manifest mediaTypes(Set<String> mediaTypes) {
		this.mediaTypes = mediaTypes;
		return this;
	}
	
	
	
}
