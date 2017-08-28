package io.cisa.taxiiserver.domain.stix.types;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;
import io.cisa.taxiiserver.domain.stix.common.KillChainPhase;

public class AttackPattern extends StixObject{
		
	@Field("name")
	private String name;
	
	@Field("description")
	private String description;
	
	@Field("kill_chain_phases")
	@JsonProperty("kill_chain_phases")
	private Set<KillChainPhase> killChainPhases = new HashSet<>();
	
	public AttackPattern() {
		super();
		setType("attack-pattern");
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

	public Set<KillChainPhase> getKillChainPhases() {
		return killChainPhases;
	}

	public void setKillChainPhases(Set<KillChainPhase> killChainPhases) {
		this.killChainPhases = killChainPhases;
	}
	
	
	
}
