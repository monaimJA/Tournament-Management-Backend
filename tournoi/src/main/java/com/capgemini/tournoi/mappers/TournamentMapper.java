package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.entity.Tournoi;
import com.capgemini.tournoi.enums.StatusTournoi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TournamentMapper {

    public Tournoi fromTournamentDtoRequest(CreateTournamentRequestDto tournamentRequestDto){
        return Tournoi.builder()
                .label(tournamentRequestDto.getLabel())
                .startDate(tournamentRequestDto.getStartDate())
                .endDate(tournamentRequestDto.getEndDate())
                .statusTournoi(StatusTournoi.INSCRIPION)
                .teams(new ArrayList<>())
                .build();
    }
}
