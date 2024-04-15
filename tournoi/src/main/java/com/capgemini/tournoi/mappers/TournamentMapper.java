package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTournamentAndMatch;

import java.util.ArrayList;


public class TournamentMapper {

    public static Tournament fromTournamentDtoRequest(CreateTournamentRequestDto tournamentRequestDto){
        return Tournament.builder()
                .label(tournamentRequestDto.getLabel())
                .startDate(tournamentRequestDto.getStartDate())
                .endDate(tournamentRequestDto.getEndDate())
                .statusTournament(StatusTournamentAndMatch.INSCRIPTION)
                .inProgress(true)
                .teams(new ArrayList<>())
                .build();
    }

    public static TournamentResponseDto fromTournament(Tournament tournament) {
        return TournamentResponseDto.builder()
                .id(tournament.getId())
                .endDate(tournament.getEndDate())
                .label(tournament.getLabel())
                .startDate(tournament.getStartDate())
                .statusTournamentAndMatch(tournament.getStatusTournament())
                .build();
    }
}
