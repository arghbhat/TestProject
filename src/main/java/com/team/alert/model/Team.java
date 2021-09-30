package com.team.alert.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Team {
	@Id
	@GeneratedValue(generator="id-generator")
	@GenericGenerator(name="id-generator",parameters=@Parameter(name="prefix",value="Team"),
	strategy="com.team.alert.generator.CustomIDGenerator")
	private String teamId;
	
	@NotBlank(message="Team name cannot be blank")
	private String teamName;
	
	@OneToMany(targetEntity = Developer.class,cascade= {CascadeType.ALL})
	@JoinColumn(name="developerTeamId")
	@JsonProperty(value="developers")
	@Valid
	private List<Developer> teamMembers;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<Developer> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(List<Developer> teamMembers) {
		this.teamMembers = teamMembers;
	}
	
	
}
