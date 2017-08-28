package io.cisa.taxiiserver.domain.stix.types;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;
import io.cisa.taxiiserver.domain.stix.common.KillChainPhase;

public class Indicator extends StixObject{
	@Field("name")
	private String name;
	
	@Field("description")
	private String description;
	
	@Field("pattern")
	private String pattern;
	
	@Field("valid_from")
	@JsonProperty("valid_from")
	private ZonedDateTime validFrom;
	
	@Field("valid_until")
	@JsonProperty("valid_until")
	private ZonedDateTime validUntil;
	
	@Field("kill_chain_phases")
	@JsonProperty("kill_chain_phases")
	private Set<KillChainPhase> killChainPhases = new HashSet<>();

	public Indicator() {
		super();
		setType("indicator");
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


	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public ZonedDateTime getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(ZonedDateTime validFrom) {
		this.validFrom = validFrom;
	}

	public ZonedDateTime getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(ZonedDateTime validUntil) {
		this.validUntil = validUntil;
	}

	public Set<KillChainPhase> getKillChainPhases() {
		return killChainPhases;
	}

	public void setKillChainPhases(Set<KillChainPhase> killChainPhases) {
		this.killChainPhases = killChainPhases;
	}
	
	
}
