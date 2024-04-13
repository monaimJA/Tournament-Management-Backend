package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.dtos.MatchResponseDto;
import com.capgemini.tournoi.dtos.MatchResponseDtoFront;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.*;
import com.capgemini.tournoi.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class MatchMapper {

    @Autowired
    private static TeamRepository teamRepository;
    public static Match fromMatchDTO(MatchRequestDTO matchRequestDTO){
        Team team1= teamRepository.findById(matchRequestDTO.getTeamId1()).orElse(null);
        Team team2 = teamRepository.findById(matchRequestDTO.getTeamId1()).orElse(null);
        if(team1 == null || team2 == null){
            throw new IllegalArgumentException("Equipe non trouve avec les ID fournis");
        }
        return Match.builder()
                .team1(team1)
                .team2(team2)
                .startTime(matchRequestDTO.getStartTime())
                .score(Score.builder()
                        .goals(new ArrayList<>())
                        .build())
                .statusMatch(matchRequestDTO.getStatusTournamentAndMatch())
                .tournament(matchRequestDTO.getTournament())
                .cards(new ArrayList<>())
                .build();

    }

    public static MatchResponseDtoFront fromMatchToFront(Match match){
        new MatchResponseDtoFront();
        return MatchResponseDtoFront.builder()
                .team1Name(match.getTeam1().getName())
                .team2Name(match.getTeam2().getName())
                .tournamentLabel(match.getTournament().getLabel())
                .winnerTeamName(match.getWinnerTeam().getName())
                .team1Score(match.getScore().getGoals().size())
                .team2Score(match.getScore().getGoals().size())
                .build();

    }



    public static MatchResponseDto fromMatch(Match match) {
        new MatchResponseDto();
        return MatchResponseDto.builder()
                .id(match.getId())
                .startTime(match.getStartTime())
                .teamId1(match.getTeam1().getId())
                .teamId2(match.getTeam2().getId())
                .winnerTeamId(match.getWinnerTeam().getId().intValue())
                .score(match.getScore())
                .build();
    }





}
