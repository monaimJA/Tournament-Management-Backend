package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Score;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class MatchMapper {

    @Autowired
    private TeamRepository teamRepository;
    public Match fromMatchDTO(MatchRequestDTO matchRequestDTO){
        Team team1= teamRepository.findById(matchRequestDTO.getTeamId1()).orElse(null);
        Team team2 = teamRepository.findById(matchRequestDTO.getTeamId1()).orElse(null);
        if(team1 == null || team2 == null){
            throw new IllegalArgumentException("Equipe non trouve avec les ID fournis");
        }
        return Match.builder()
                .team1(team1)
                .team2(team2)
                .startTime(matchRequestDTO.getStartTime())
                .score(Score.builder()
                        .goals(new ArrayList<>())
                        .build())
                .cards(new ArrayList<>())
                .build();

    }

}
