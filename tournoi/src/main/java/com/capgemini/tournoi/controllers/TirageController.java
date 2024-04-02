package com.capgemini.tournoi.controllers;


import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.repos.TeamRepository;
import com.capgemini.tournoi.repos.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tirageAuSort")
public class TirageController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/{tournoiId}")
    public List<List<Team>> lancer(@PathVariable long tournoiId){
        List<Team> teams=teamRepository.findByTournamentId(tournoiId);
        Collections.shuffle(teams);
        return getLists(teams);
    }

    private static List<List<Team>> getLists(List<Team> teams) {
        List<List<Team>> groupedLists = new ArrayList<>();
        for (int i = 0; i < teams.size(); i += 2) {
            List<Team> sublist = new ArrayList<>();
            sublist.add(teams.get(i));
            if (i + 1 < teams.size()) {
                sublist.add(teams.get(i + 1));
            }
            groupedLists.add(sublist);
        }
        return groupedLists;
    }
}
