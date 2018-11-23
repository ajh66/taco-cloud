package com.ajh.taco.security;

import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix="taco.security")
@Validated
public class SecurityConfigProps {
	@NotBlank(message="sampleUserName is required")
	private String sampleUserName;

	@NotBlank(message="samplePassword is required")
	private String samplePassword;

	@NotBlank(message="sampleUserRole is required")
	private String sampleUserRole;

	public String getSampleUserName() {
		return sampleUserName;
	}

	public void setSampleUserName(String sampleUserName) {
		this.sampleUserName = sampleUserName;
	}

	public String getSamplePassword() {
		return samplePassword;
	}

	public void setSamplePassword(String samplePassword) {
		this.samplePassword = samplePassword;
	}

	public String getSampleUserRole() {
		return sampleUserRole;
	}

	public void setSampleUserRole(String sampleUserRole) {
		this.sampleUserRole = sampleUserRole;
	}

}
