package io.cisa.taxiiserver.domain.stix;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.cisa.taxiiserver.domain.stix.common.CommonProperties;

@Document(collection="objects")
public class StixObject extends CommonProperties{
	
	@Id
	private String id;
	
	@Field("type")
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
