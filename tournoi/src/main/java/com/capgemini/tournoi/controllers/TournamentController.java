package com.capgemini.tournoi.controllers;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournoi;
import com.capgemini.tournoi.enums.StatusTournoi;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentDateException;
import com.capgemini.tournoi.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("tournament")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @PostMapping("/create")
    public Tournoi createTournament(@RequestBody CreateTournamentRequestDto tournamentDto) throws TournamentDateException {
        return tournamentService.createTournament(tournamentDto);
    }
    @GetMapping("/all")
    public List<Tournoi> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }
    @GetMapping("/{id}")
    public Tournoi getTournamentById(@PathVariable Long id) throws TournamentNotFoundException {
        return tournamentService.getTournamentById(id);
    }
    @GetMapping("/{id}/scorers")
    public HashMap<String, Integer> tournamentScorers(@PathVariable(name = "id") Long tournamentId) throws TournamentNotFoundException {
        return tournamentService.tournamentScorers(tournamentId);
    }
    @GetMapping("/{id}")
    public List<Team> getTeamsByTournamentStatus(@PathVariable(name = "id") Long tournamentId,@RequestBody StatusTournoi status) throws TournamentNotFoundException {
        return tournamentService.getTeamsByTournamentStatus(tournamentId, status);
    }

}
