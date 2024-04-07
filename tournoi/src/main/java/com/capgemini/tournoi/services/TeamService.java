package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.error.ChangePlayersOfTeamDuringTournamentException;
import com.capgemini.tournoi.error.PlayerExistInAnotherTeamException;
import com.capgemini.tournoi.error.PlayerNotFoundException;
import com.capgemini.tournoi.globalExceptions.MaximumPlayersLimitException;
import com.capgemini.tournoi.globalExceptions.PlayersNotSufficientException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;

import java.util.List;

public interface TeamService {
    TeamDto saveTeam(TeamDto teamDto) throws MaximumPlayersLimitException, PlayersNotSufficientException, PlayerExistInAnotherTeamException;
    TeamDto inscription(TeamDto teamDto) throws MaximumPlayersLimitException, PlayersNotSufficientException, PlayerExistInAnotherTeamException;
    List<TeamDto> teamsList();
    List<TeamDto> teamsListInTournament(Long tournamentId);
    TeamDto getTeam(Long id) throws TeamNotFoundException;
    TeamDto updateStatus(Long id,TeamDto teamDto);

    TeamDto changeTeamPlayer(Long playerId,PlayerDto playerDto,Long tournamentId) throws PlayerNotFoundException, TournamentNotFoundException, ChangePlayersOfTeamDuringTournamentException;



}
