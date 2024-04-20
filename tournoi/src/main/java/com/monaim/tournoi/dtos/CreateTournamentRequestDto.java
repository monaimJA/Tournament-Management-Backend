package com.monaim.tournoi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTournamentRequestDto {
    private String label;
    private LocalDate startDate;
    private LocalDate endDate;
}
