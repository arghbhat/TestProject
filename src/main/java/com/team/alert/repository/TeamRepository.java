package com.team.alert.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.team.alert.model.Team;
import com.team.alert.model.Developer;

public interface TeamRepository extends CrudRepository<Team, String>{
	
	@Query("select dev from Developer dev FETCH ALL properties  where dev.developerTeamId=:teamId")
	List<Developer> findByDeveloper_Id(@Param("teamId")String teamId);
	
	@Query("select team from Team team FETCH ALL properties  where team.teamName=:teamName")
	Optional<Team> findTeamDetails(@Param("teamName") String teamname);

}
