package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Player;
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
        private int team2Score;
        private String winnerTeamName;
        private int team1Score;

    }


