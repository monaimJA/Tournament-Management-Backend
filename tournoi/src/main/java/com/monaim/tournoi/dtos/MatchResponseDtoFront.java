package com.monaim.tournoi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponseDtoFront {
        private String tournamentLabel;
        private String team1Name;
        private String team2Name;
        private int team2Score;
        private String winnerTeamName;
        private int team1Score;

    }


