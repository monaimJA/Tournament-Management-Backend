package com.capgemini.tournoi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponseDtoFront {
        private String tournamentLabel;
        private String team1Name;
        private String team2Name;
        private int team1Score;
        private int team2Score;
        private String winnerTeamName;
        private List<String> team1Players;
        private List<String> team2Players;
    }


