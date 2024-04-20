package com.monaim.tournoi.controllers;

import com.monaim.tournoi.dtos.TeamDto;
import com.monaim.tournoi.dtos.TeamGetDto;
import com.monaim.tournoi.entity.Site;
import com.monaim.tournoi.globalExceptions.MaximumPlayersLimitException;
import com.monaim.tournoi.globalExceptions.PlayersNotSufficientException;
import com.monaim.tournoi.globalExceptions.TeamNotFoundException;
import com.monaim.tournoi.globalExceptions.TwoTeamsPlayerException;
import com.monaim.tournoi.services.TeamService;
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

    @GetMapping("/site/all")
    public List<Site> site(){
        return teamService.getSites();
    }

}
