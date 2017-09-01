package io.cisa.taxiiserver.domain.stix.types;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;

public class Campaign extends StixObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3567272571923422964L;

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
	
	@Field("objective")
	private String objective;
	
	public Campaign() {
		super();
		setType("campaign");
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
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	
}
