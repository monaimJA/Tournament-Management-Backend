package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.TeamDto;
import com.monaim.tournoi.dtos.TeamGetDto;
import com.monaim.tournoi.entity.Site;
import com.monaim.tournoi.globalExceptions.MaximumPlayersLimitException;
import com.monaim.tournoi.globalExceptions.PlayersNotSufficientException;
import com.monaim.tournoi.globalExceptions.TeamNotFoundException;
import com.monaim.tournoi.globalExceptions.TwoTeamsPlayerException;

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
