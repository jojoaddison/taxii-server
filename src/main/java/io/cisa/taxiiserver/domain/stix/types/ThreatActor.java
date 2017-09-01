package io.cisa.taxiiserver.domain.stix.types;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;

public class ThreatActor extends StixObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1604793873853205314L;

	@Field("name")
	private String name;
	
	@Field("description")
	private String description;
	
	@Field("aliases")
	private Set<String> aliases = new HashSet<>();
	
	@Field("roles")
	private Set<String> roles = new HashSet<>();
	
	@Field("goals")
	private Set<String> goals = new HashSet<>();
	
	@Field("sophiscation")
	private String sophiscation;
	
	@Field("resource_level")
	@JsonProperty("resource_level")
	private String resourceLevel;
	
	@Field("primary_motivation")
	@JsonProperty("primary_motivation")
	private String primaryMotivation;
	
	@Field("secondary_motivations")
	@JsonProperty("secondary_motivations")
	private Set<String> secondaryMotivations;
	
	@Field("personal_motivations")
	@JsonProperty("personal_motivations")
	private Set<String> personalMotivations;
	
	

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

	public Set<String> getAliases() {
		return aliases;
	}

	public void setAliases(Set<String> aliases) {
		this.aliases = aliases;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Set<String> getGoals() {
		return goals;
	}

	public void setGoals(Set<String> goals) {
		this.goals = goals;
	}

	public String getSophiscation() {
		return sophiscation;
	}

	public void setSophiscation(String sophiscation) {
		this.sophiscation = sophiscation;
	}

	public String getResourceLevel() {
		return resourceLevel;
	}

	public void setResourceLevel(String resourceLevel) {
		this.resourceLevel = resourceLevel;
	}

	public String getPrimaryMotivation() {
		return primaryMotivation;
	}

	public void setPrimaryMotivation(String primaryMotivation) {
		this.primaryMotivation = primaryMotivation;
	}

	public Set<String> getSecondaryMotivations() {
		return secondaryMotivations;
	}

	public void setSecondaryMotivations(Set<String> secondaryMotivations) {
		this.secondaryMotivations = secondaryMotivations;
	}

	public Set<String> getPersonalMotivations() {
		return personalMotivations;
	}

	public void setPersonalMotivations(Set<String> personalMotivations) {
		this.personalMotivations = personalMotivations;
	}
	
	
}
