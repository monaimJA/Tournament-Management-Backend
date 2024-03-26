package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;

import java.util.List;

public interface TeamService {
    TeamDto saveTeam(TeamDto teamDto);
    List<TeamDto> teamsList();
    List<TeamDto> teamsListInTournament(Long tournamentId);
    TeamDto getTeam(Long id) throws TeamNotFoundException;
    TeamDto updateStatus(Long id,TeamDto teamDto);


}
