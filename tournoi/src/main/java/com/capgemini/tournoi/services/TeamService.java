package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.dtos.TeamGetDto;
import com.capgemini.tournoi.entity.Site;
import com.capgemini.tournoi.globalExceptions.MaximumPlayersLimitException;
import com.capgemini.tournoi.globalExceptions.PlayersNotSufficientException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TwoTeamsPlayerException;

import java.util.List;

public interface TeamService {
    TeamDto saveTeam(TeamDto teamDto) throws MaximumPlayersLimitException, PlayersNotSufficientException;
    TeamDto inscription(TeamDto teamDto) throws MaximumPlayersLimitException, PlayersNotSufficientException, TwoTeamsPlayerException;
    List<TeamDto> teamsList();
    List<TeamGetDto> teamsListInTournament(Long tournamentId);
    TeamDto getTeam(Long id) throws TeamNotFoundException;
    TeamDto updateStatus(Long id,TeamDto teamDto);
    List<Site> getSites();



}
