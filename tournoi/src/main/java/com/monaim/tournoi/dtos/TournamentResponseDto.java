package com.monaim.tournoi.dtos;

import com.monaim.tournoi.enums.StatusTournamentAndMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentResponseDto {
    private Long id ;
    private String label;
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusTournamentAndMatch statusTournamentAndMatch;
}
