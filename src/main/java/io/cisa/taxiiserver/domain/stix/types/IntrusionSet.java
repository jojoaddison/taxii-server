package io.cisa.taxiiserver.domain.stix.types;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;

public class IntrusionSet extends StixObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4647630051959364601L;

	@Field("name")
	private String name;
	
	@Field("description")
	private String description;
	
	@Field("aliases")
	private Set<String> aliases = new HashSet<>();
	
	@Field("first_seen")
	@JsonProperty("first_seen")
	private ZonedDateTime firstSeen;
	
	@Field("last_seen")
	@JsonProperty("last_seen")
	private ZonedDateTime lastSeen;
	
	@Field("goals")
	private Set<String> goals;
	
	@Field("resource_level")
	@JsonProperty("resource_level")
	private String resourceLevel;
	
	@Field("primary_motivation")
	@JsonProperty("primary_motivation")
	private String primaryMotivation;
	
	@Field("secondary_motivations")
	@JsonProperty("secondary_motivations")
	private Set<String> secondaryMotivations;

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

	public ZonedDateTime getFirstSeen() {
		return firstSeen;
	}

	public void setFirstSeen(ZonedDateTime firstSeen) {
		this.firstSeen = firstSeen;
	}

	public ZonedDateTime getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(ZonedDateTime lastSeen) {
		this.lastSeen = lastSeen;
	}

	public Set<String> getGoals() {
		return goals;
	}

	public void setGoals(Set<String> goals) {
		this.goals = goals;
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
	
}
