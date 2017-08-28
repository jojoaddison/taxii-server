package io.cisa.taxiiserver.domain.stix.common;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="external_reference")
public class ExternalReference {
	@Field("source_name")
	@JsonProperty("source_name")
	private String sourceName;
	
	@Field("description")
	private String description;
	
	@Field("url")
	private String url;
	
	@Field("hashes")
	private Map<String, String> hashes;
	
	@Field("external_id")
	@JsonProperty("external_id")
	private String externalId;

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHashes() {
		return hashes;
	}

	public void setHashes(Map<String, String> hashes) {
		this.hashes = hashes;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	
}
