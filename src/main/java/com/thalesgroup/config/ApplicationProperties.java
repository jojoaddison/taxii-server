package com.thalesgroup.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	
	private final Headers headers = new Headers();
	
	public Headers getHeaders() {
		return headers;
	}
	
	public static class Headers{
		private String accept;
		public void setAccept(String accept) {
			this.accept = accept;
		}
		public String getAccept() {
			return this.accept;
		}
	}

}
