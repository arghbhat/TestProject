package com.team.alert.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team.alert.customexception.AlertException;
import com.team.alert.customexception.DevelopersMissedAlertException;
import com.team.alert.customexception.TeamCreationException;
import com.team.alert.model.Team;
import com.team.alert.service.TeamService;

@RestController
public class TeamController {
	
	@Autowired
	TeamService teamService;
	
	@RequestMapping(value="/team",method=RequestMethod.POST)
	public String createTeam(@Valid @RequestBody Team team,Errors errors)throws TeamCreationException{
		if(errors.hasErrors()) {
			TeamCreationException exception=new TeamCreationException(errors.getAllErrors()
					.stream()
					.map(error->error.getDefaultMessage())
					.collect(Collectors.joining(",")));
			throw exception;
		}
		return teamService.createTeam(team);
	}
	
	@RequestMapping(value="/{team_id}/alert",method=RequestMethod.POST)
	public String createTeam(@PathVariable(value="team_id") String teamId)throws AlertException,DevelopersMissedAlertException{
		return teamService.alertTeam(teamId);
	}
}
