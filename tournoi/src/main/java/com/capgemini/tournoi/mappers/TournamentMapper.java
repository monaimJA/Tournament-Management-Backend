package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTournoi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TournamentMapper {

    public Tournament fromTournamentDtoRequest(CreateTournamentRequestDto tournamentRequestDto){
        return Tournament.builder()
                .label(tournamentRequestDto.getLabel())
                .startDate(tournamentRequestDto.getStartDate())
                .endDate(tournamentRequestDto.getEndDate())
                .statusTournoi(StatusTournoi.INSCRIPION)
                .teams(new ArrayList<>())
                .build();
    }

    public TournamentResponseDto fromTournament(Tournament tournament) {
        return TournamentResponseDto.builder()
                .id(tournament.getId())
                .endDate(tournament.getEndDate())
                .label(tournament.getLabel())
                .startDate(tournament.getStartDate())
                .statusTournoi(tournament.getStatusTournoi())
                .build();
    }
}
