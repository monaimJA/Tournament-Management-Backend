package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.enums.StatusTournamentAndMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponseDtoInProgress {
    private String team1Name;
    private String team2Name;
    private LocalDateTime startTime;
    private StatusTournamentAndMatch statusTournamentAndMatch;
    private Integer scoreTeam1;
    private Integer scoreTeam2;
    private  String nameWinnerTeam;
    private List<String> playersTeam1;
    private List<String> playersTeam2;
}
