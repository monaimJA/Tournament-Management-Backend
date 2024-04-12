package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Score;
import com.capgemini.tournoi.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponseDto {
    private long id;
    private LocalDate startTime;
    private Long teamId1;
    private Long teamId2;
    private Score score;
    private int winnerTeamId;


}
