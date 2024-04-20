package com.monaim.tournoi.dtos;

import com.monaim.tournoi.entity.Tournament;
import com.monaim.tournoi.enums.StatusTournamentAndMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class MatchRequestDTO {
    private long id;
    private LocalDateTime startTime;
    private Long teamId1;
    private Long teamId2;
    private StatusTournamentAndMatch statusTournamentAndMatch;
    private Tournament tournament;

}
