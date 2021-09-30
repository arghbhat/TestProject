package com.team.alert;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.alert.model.Developer;
import com.team.alert.model.Team;

@SpringBootTest
@AutoConfigureMockMvc
class AlertMechanismSystemApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@Order(1)
	public void testForSuccessfullTeamPost()throws Exception {
		Team team=new Team();
		team.setTeamName("Sunview");
		Developer dev=new Developer();
		dev.setDeveloperName("Arghya");
		dev.setDeveloperPhoneNumber("8584922846");
		List<Developer> developers=new ArrayList<>();
		developers.add(dev);
		team.setTeamMembers(developers);
		
		this.mockMvc.perform(post("/team").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(team))).andDo(print())
		.andExpect(status().isOk()).andExpect( content().string(containsString(team.getTeamName()+" team successfully created")));
	}
	
	@Test
	@Order(2)
	public void testForSuccessfullAlert()throws Exception {
		
		this.mockMvc.perform(post("/Team-101/alert")).andDo(print())
		.andExpect(status().isOk()).andExpect(content().string(containsString("Alert sent successfully for team Team-101")));
	}

}
