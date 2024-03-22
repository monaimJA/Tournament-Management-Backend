package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournoi;
import com.capgemini.tournoi.enums.StatusTournoi;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentDateException;

import java.util.HashMap;
import java.util.List;

public interface TournamentService {
    Tournoi createTournament(CreateTournamentRequestDto tournamentDto) throws TournamentDateException;
    List<Tournoi> getAllTournaments();
    Tournoi getTournamentById(Long id) throws TournamentNotFoundException;
    HashMap<String, Integer> tournamentScorers(Long tournamentId) throws TournamentNotFoundException;
    List<Team> getTeamsByTournamentStatus(Long tournamentId, StatusTournoi status) throws TournamentNotFoundException;
}
