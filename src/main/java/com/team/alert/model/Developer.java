package com.team.alert.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Developer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long developerId;
	
	@JsonProperty(value="name")
	@NotBlank(message="Developer name cannot be blank")
	@Pattern(regexp="^[A-Za-z]+$",message="Developer name should only contain alphabets")
	private String developerName;
	
	
	@JsonProperty(value="phoneNumber")
	@NotBlank(message="Developer phone number cannot be blank")
	@Size(min=10,max=10,message="Phone number should be 10 digits only")
	@Digits(integer=10,fraction=0,message="Phone Number cannot be non numeric")
	private String developerPhoneNumber;
	
	
	private String developerTeamId;


	public long getDeveloperId() {
		return developerId;
	}


	public void setDeveloperId(long developerId) {
		this.developerId = developerId;
	}


	public String getDeveloperName() {
		return developerName;
	}


	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}


	public String getDeveloperPhoneNumber() {
		return developerPhoneNumber;
	}


	public void setDeveloperPhoneNumber(String developerPhoneNumber) {
		this.developerPhoneNumber = developerPhoneNumber;
	}


	public String getDeveloperTeamId() {
		return developerTeamId;
	}


	public void setDeveloperTeamId(String developerTeamId) {
		this.developerTeamId = developerTeamId;
	}
	
	
	
	
}
