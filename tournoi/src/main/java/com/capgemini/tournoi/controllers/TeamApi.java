package com.capgemini.tournoi.controllers;

import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.dtos.TeamGetDto;
import com.capgemini.tournoi.globalExceptions.MaximumPlayersLimitException;
import com.capgemini.tournoi.globalExceptions.PlayersNotSufficientException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TwoTeamsPlayerException;
import com.capgemini.tournoi.services.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@AllArgsConstructor
public class TeamApi {
    TeamService teamService;

    @PostMapping("/team")
    public TeamDto saveTeam(@RequestBody  TeamDto teamDto) throws PlayersNotSufficientException, MaximumPlayersLimitException {
        return teamService.saveTeam(teamDto);
    }

    @PostMapping("/inscription")
    public TeamDto inscription(@RequestBody  TeamDto teamDto) throws PlayersNotSufficientException, MaximumPlayersLimitException , TwoTeamsPlayerException {
        return teamService.inscription(teamDto);
    }


    @GetMapping("/team/all")
    public List<TeamDto> teams(){
        return teamService.teamsList();
    }

    @GetMapping("/team/tournoi/{id}")
    public List<TeamGetDto> teamsInTournament(@PathVariable Long id){
        return teamService.teamsListInTournament(id);
    }

    @GetMapping("/team/{id}")
    public TeamDto getTeam(@PathVariable Long id) throws TeamNotFoundException {
        return teamService.getTeam(id);
    }

    @PutMapping("team/{id}")
    public TeamDto updateStatus(@PathVariable Long id,@RequestBody TeamDto teamDto){
        return teamService.updateStatus(id,teamDto);
    }



}
