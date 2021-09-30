package com.team.alert.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertRequest {

	@JsonProperty(value="phone_number")
	private String phoneNumber;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
