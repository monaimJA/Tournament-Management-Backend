package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.dtos.ModifyTournamentRequestDto;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.error.TournamentAlreadyInProgressException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentDateException;

import java.util.HashMap;
import java.util.List;

public interface TournamentService {
    Tournament createTournament(CreateTournamentRequestDto tournamentDto) throws TournamentDateException, TournamentAlreadyInProgressException;
    List<TournamentResponseDto> getAllTournaments();
    TournamentResponseDto getTournamentById(Long id) throws TournamentNotFoundException;
    HashMap<String, Integer> tournamentScorers(Long tournamentId) throws TournamentNotFoundException;
    TournamentResponseDto addTeamToTournament(Long tournamentId, Long teamId) throws TeamNotFoundException, TournamentNotFoundException;
    TournamentResponseDto deleteTeamFromTournament(Long tournamentId, Long teamId) throws TeamNotFoundException, TournamentNotFoundException;
    TournamentResponseDto modifyTournament(Long tournamentId, ModifyTournamentRequestDto updatedTournament) throws TournamentNotFoundException;
}
