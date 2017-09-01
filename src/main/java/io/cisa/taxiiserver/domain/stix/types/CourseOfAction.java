package io.cisa.taxiiserver.domain.stix.types;

import org.springframework.data.mongodb.core.mapping.Field;

import io.cisa.taxiiserver.domain.stix.StixObject;

public class CourseOfAction extends StixObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3853362027878920867L;

	@Field("name")
	private String name;
	
	@Field("description")
	private String description;
	
	@Field("action")
	private String action;
	
	public CourseOfAction() {
		super();
		setType("course-of-action");
	}

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
