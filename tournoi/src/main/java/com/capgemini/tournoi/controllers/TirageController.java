package com.capgemini.tournoi.controllers;


import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
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
    private TournamentRepository tournamentRepository;

    @GetMapping("/{tournoiId}")
    public List<List<Long>> lancer(@PathVariable long tournoiId){
        Optional<Tournament> tournoiOptional=tournamentRepository.findById(tournoiId);
        if(tournoiOptional.isPresent()){
            Collections.shuffle(tournoiOptional.get().getTeams());
            return getLists(tournoiOptional);
        }else{
            throw new RuntimeException("tournoi with id="+tournoiId+" doesn't exist");
        }
    }

    private static List<List<Long>> getLists(Optional<Tournament> tournoiOptional) {
        List<Team> teams= tournoiOptional.get().getTeams();
        List<List<Long>> groupedLists = new ArrayList<>();
        for (int i = 0; i < teams.size(); i += 2) {
            List<Long> sublist = new ArrayList<>();
            sublist.add(teams.get(i).getId());
            if (i + 1 < teams.size()) {
                sublist.add(teams.get(i + 1).getId());
            }
            groupedLists.add(sublist);
        }
        return groupedLists;
    }
}
