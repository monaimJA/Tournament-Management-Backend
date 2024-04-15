package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Goal;
import com.capgemini.tournoi.entity.Score;
import com.capgemini.tournoi.enums.StatusTournamentAndMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponseDto {
    private long id;
    private LocalDate startTime;
    private TeamDto nameTeam1;
    private TeamDto nameTeam2;
    private List<Goal> goals;
    private String labelTournament;
    private StatusTournamentAndMatch statusTournamentAndMatch;
    private List<String> namePlayersTeam1;
    private List<String> namePlayersTeam2;
    private String nameWinnerTeam;


}
