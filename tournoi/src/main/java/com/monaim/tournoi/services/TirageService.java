package com.monaim.tournoi.services;

import com.monaim.tournoi.entity.Team;
import com.monaim.tournoi.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TirageService {

    @Autowired
    private TeamRepository teamRepository;
    public List<List<Team>> lancer(List<Team> teams){
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
