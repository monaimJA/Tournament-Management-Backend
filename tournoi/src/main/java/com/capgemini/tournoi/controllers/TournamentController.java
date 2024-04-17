package com.capgemini.tournoi.controllers;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.dtos.ModifyTournamentRequestDto;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.error.TournamentAlreadyInProgressException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentDateException;
import com.capgemini.tournoi.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/tournament")
@CrossOrigin("*")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @PostMapping("/create")
    public Tournament createTournament(@RequestBody CreateTournamentRequestDto tournamentDto) throws TournamentDateException, TournamentAlreadyInProgressException {
        return tournamentService.createTournament(tournamentDto);
    }
    @GetMapping("/all")
    public List<TournamentResponseDto> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }
    @GetMapping("/{id}")
    public TournamentResponseDto getTournamentById(@PathVariable Long id) throws TournamentNotFoundException {
        return tournamentService.getTournamentById(id);
    }
    @GetMapping("/{id}/scorers")
    public HashMap<String, Integer> tournamentScorers(@PathVariable(name = "id") Long tournamentId) throws TournamentNotFoundException {
        return tournamentService.tournamentScorers(tournamentId);
    }
    @PostMapping("/{id}/teams/{teamId}/add")
    public TournamentResponseDto addTeamToTournament(@PathVariable(name = "id") Long tournamentId,@PathVariable Long teamId) throws TeamNotFoundException, TournamentNotFoundException {
        return tournamentService.addTeamToTournament(tournamentId, teamId);
    }
    @PostMapping("/{id}/teams/{teamId}/delete")
    public TournamentResponseDto deleteTeamFromTournament(@PathVariable(name = "id") Long tournamentId,@PathVariable Long teamId) throws TeamNotFoundException, TournamentNotFoundException {
        return tournamentService.deleteTeamFromTournament(tournamentId, teamId);
    }
    @PatchMapping("/{id}")
    public TournamentResponseDto modifyTournament(@PathVariable Long id, @RequestBody ModifyTournamentRequestDto updatedTournament) throws TournamentNotFoundException {
        return tournamentService.modifyTournament(id, updatedTournament);
    }

    @GetMapping("/current")
    public Tournament getCurrentTournament() {
        return tournamentService.getCurrentTournament();
    }
}
