package com.monaim.tournoi.dtos;

import com.monaim.tournoi.entity.Goal;
import com.monaim.tournoi.enums.StatusTournamentAndMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponseDto {
    private long id;
    private LocalDateTime startTime;
    private TeamDto nameTeam1;
    private TeamDto nameTeam2;
    private List<Goal> goals;
    private String labelTournament;
    private StatusTournamentAndMatch statusTournamentAndMatch;
    private List<String> namePlayersTeam1;
    private List<String> namePlayersTeam2;
    private String nameWinnerTeam;


}
