package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTournamentAndMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class MatchRequestDTO {
    private long id;
    private LocalDate startTime;
    private Long teamId1;
    private Long teamId2;
    private StatusTournamentAndMatch statusTournamentAndMatch;
    private Tournament tournament;

}
