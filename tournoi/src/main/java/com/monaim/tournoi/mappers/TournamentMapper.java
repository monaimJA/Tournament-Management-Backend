package com.monaim.tournoi.mappers;

import com.monaim.tournoi.dtos.CreateTournamentRequestDto;
import com.monaim.tournoi.dtos.TournamentResponseDto;
import com.monaim.tournoi.entity.Tournament;
import com.monaim.tournoi.enums.StatusTournamentAndMatch;

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
