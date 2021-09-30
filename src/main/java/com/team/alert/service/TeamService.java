package com.team.alert.service;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.team.alert.customexception.AlertException;
import com.team.alert.customexception.DevelopersMissedAlertException;
import com.team.alert.customexception.TeamCreationException;
import com.team.alert.model.AlertRequest;
import com.team.alert.model.AlertResponse;
import com.team.alert.model.Developer;
import com.team.alert.model.Team;
import com.team.alert.repository.TeamRepository;

@Service
public class TeamService {

	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	@Qualifier("alertRestTemplate")
	RestTemplate alertTemplate;
	
	@Value("${alert.url}")
	String url;
	
	public String createTeam(Team team) throws TeamCreationException{
		Optional<Team> teamDetails=teamRepository.findTeamDetails(team.getTeamName());
		
		if(teamDetails.isPresent()) {
			throw new TeamCreationException("Team already exists");
		}
		
		try {
			teamRepository.save(team);
		}catch(Exception e) {
			throw new TeamCreationException("Failed to save team details in repository . Nested exception :- "+e.getMessage());
			
		}
		return team.getTeamName()+ " team successfully created";
	}
	
	public String alertTeam(String teamId)throws AlertException,DevelopersMissedAlertException{
		List<Developer> developers=teamRepository.findByDeveloper_Id(teamId);
		
		if(developers.size()>0) {
			List<CompletableFuture<?>> asyncAlertList=new ArrayList<>();
			
			developers.forEach(developer->{
				CompletableFuture<Serializable> devloperAlert=CompletableFuture.supplyAsync(()->{
					AlertRequest requestBody=new AlertRequest();
					requestBody.setPhoneNumber(developer.getDeveloperPhoneNumber());
					RequestEntity<AlertRequest> request;
					ResponseEntity<AlertResponse> response=null;
					
					try {
						request=RequestEntity.
								post(new URI(url))
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.body(requestBody);
						response=alertTemplate.exchange(request, AlertResponse.class);
						if(response.getStatusCode()!=HttpStatus.OK) {
							return  new AlertException("Failed to send alert for developer "+developer.getDeveloperName());
						}
					}catch(Exception e) {
						return  new AlertException("Failed to send alert for developer "+developer.getDeveloperName());
					}
					System.out.println("Alert sent successfully for developer "+developer.getDeveloperName());
					return response.getBody().getSuccess();
				});
				asyncAlertList.add(devloperAlert);
			});
			try {
				List<?> exceptionList=Stream.of(asyncAlertList.toArray(new CompletableFuture[asyncAlertList.size()]))
						.map(CompletableFuture::join)
						.filter(item->{
							if(item instanceof AlertException) {
								return true;
							}else {
								return false;
							}
						}).collect(Collectors.toList());
				if(exceptionList.size()>0) {
					DevelopersMissedAlertException missedAlertException=new DevelopersMissedAlertException();
					exceptionList.forEach(exception->{
						missedAlertException.getMessages().add(((AlertException)exception).getMessage());
					});
					throw missedAlertException; 
				}
			}catch(Exception e) {
				throw new AlertException("Failed to send alert for all the developers in team "+teamId);
			}
		}else {
			throw new AlertException("Team does not exist with id "+teamId);
		}
		return "Alert sent successfully for team "+teamId;
	}
}


