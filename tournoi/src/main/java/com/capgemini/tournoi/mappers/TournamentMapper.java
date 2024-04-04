package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTournament;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


public class TournamentMapper {

    public static Tournament fromTournamentDtoRequest(CreateTournamentRequestDto tournamentRequestDto){
        return Tournament.builder()
                .label(tournamentRequestDto.getLabel())
                .startDate(tournamentRequestDto.getStartDate())
                .endDate(tournamentRequestDto.getEndDate())
                .statusTournament(StatusTournament.INSCRIPTION)
                .teams(new ArrayList<>())
                .build();
    }

    public static TournamentResponseDto fromTournament(Tournament tournament) {
        return TournamentResponseDto.builder()
                .id(tournament.getId())
                .endDate(tournament.getEndDate())
                .label(tournament.getLabel())
                .startDate(tournament.getStartDate())
                .statusTournament(tournament.getStatusTournament())
                .build();
    }
}
