package io.cisa.taxiiserver.domain.stix.types;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cisa.taxiiserver.domain.stix.StixObject;
import io.cisa.taxiiserver.domain.stix.common.KillChainPhase;

public class Tool extends StixObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1210258751545778141L;

	@Field("name")
	private String name;
	
	@Field("description")
	private String description;
	
	@Field("kill_chain_phases")
	@JsonProperty("kill_chain_phases")
	private Set<KillChainPhase> killChainPhases;
	
	@Field("tool_version")
	@JsonProperty("toll_version")
	private String toolVersion;

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

	public String getToolVersion() {
		return toolVersion;
	}

	public void setToolVersion(String toolVersion) {
		this.toolVersion = toolVersion;
	}
	
	
}
