package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.enums.StatusTournoi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTournamentRequestDto {
    private String label;
    private Date startDate;
    private Date endDate;
}
