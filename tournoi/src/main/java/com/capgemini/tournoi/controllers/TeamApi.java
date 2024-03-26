package com.capgemini.tournoi.controllers;

import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.services.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TeamApi {
    TeamService teamService;

    @PostMapping("/team")
    public TeamDto saveTeam(@RequestBody  TeamDto teamDto){
        return teamService.saveTeam(teamDto);
    }

    @GetMapping("/team/all")
    public List<TeamDto> teams(){
        return teamService.teamsList();
    }

    @GetMapping("/team/tournoi/{id}")
    public List<TeamDto> teamsInTournament(@PathVariable Long id){
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
