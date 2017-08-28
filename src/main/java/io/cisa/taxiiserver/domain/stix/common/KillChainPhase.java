package io.cisa.taxiiserver.domain.stix.common;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="kill_chain_phase")
public class KillChainPhase {
	
	@Field("kill_chain_name")
	@JsonProperty("kill_chain_name")
	private String killChainName;
	
	@Field("phase_name")
	@JsonProperty("phase_name")
	private String phaseName;

	public String getKillChainName() {
		return killChainName;
	}

	public void setKillChainName(String killChainName) {
		this.killChainName = killChainName;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
}
