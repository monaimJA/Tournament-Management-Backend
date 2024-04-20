package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.CreateTournamentRequestDto;
import com.monaim.tournoi.dtos.ModifyTournamentRequestDto;
import com.monaim.tournoi.dtos.TournamentResponseDto;
import com.monaim.tournoi.entity.Tournament;
import com.monaim.tournoi.error.TournamentAlreadyInProgressException;
import com.monaim.tournoi.globalExceptions.TeamNotFoundException;
import com.monaim.tournoi.globalExceptions.TournamentNotFoundException;
import com.monaim.tournoi.globalExceptions.TournamentDateException;

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
    Tournament getCurrentTournament();
    void deleteTournament(Long tournamentId) throws TournamentNotFoundException;
}
