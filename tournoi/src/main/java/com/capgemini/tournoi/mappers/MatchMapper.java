package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.*;
import com.capgemini.tournoi.entity.*;
import com.capgemini.tournoi.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MatchMapper{

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMapper teamMapper;

    public  Match fromMatchDTO(MatchRequestDTO matchRequestDTO){
        Team team1= teamRepository.findById(matchRequestDTO.getTeamId1()).orElse(null);
        Team team2 = teamRepository.findById(matchRequestDTO.getTeamId1()).orElse(null);
        if(team1 == null || team2 == null){
            throw new IllegalArgumentException("Team non trouve avec les ID fournis");
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

    public  MatchResponseDto fromMatch(Match match) {
        List<String> namePlayersTeam1 =new ArrayList<>();
        for(Player player:match.getTeam1().getPlayers()){
            namePlayersTeam1.add(player.getFirstName()+" "+player.getLastName());
        }
        List<String> namePlayersTeam2 =new ArrayList<>();
        for(Player player:match.getTeam2().getPlayers()){
            namePlayersTeam2.add(player.getFirstName()+" "+player.getLastName());
        }
        MatchResponseDto match1=MatchResponseDto.builder()
                .id(match.getId())
                .startTime(match.getStartTime())
                .namePlayersTeam1(namePlayersTeam1)
                .namePlayersTeam2(namePlayersTeam2)
                .labelTournament(match.getTournament().getLabel())
                .statusTournamentAndMatch(match.getStatusMatch())
                .nameTeam1(teamMapper.fromTeam(match.getTeam1()))
                .nameTeam2(teamMapper.fromTeam(match.getTeam2()))
                .goals(match.getScore().getGoals())
                .build();
        if(match.getWinnerTeam()!=null){
            match1.setNameWinnerTeam(match.getWinnerTeam().getName());
        }
        return match1;
    }

    public MatchResponseDtoInProgress convertToDto(Match match) {
        List<String> namePlayersTeam1 =new ArrayList<>();
        for(Player player:match.getTeam1().getPlayers()){
            namePlayersTeam1.add(player.getFirstName()+" "+player.getLastName());
        }
        List<String> namePlayersTeam2 =new ArrayList<>();
        for(Player player:match.getTeam2().getPlayers()){
            namePlayersTeam2.add(player.getFirstName()+" "+player.getLastName());
        }
        MatchResponseDtoInProgress matchResponseDtoInProgress=MatchResponseDtoInProgress.builder()
                .matchId(match.getId())
                .team1Name(match.getTeam1().getName())
                .team2Name(match.getTeam2().getName())
                .startTime(match.getStartTime())
                .playersTeam1(namePlayersTeam1)
                .playersTeam2(namePlayersTeam2)
                .scoreTeam1(match.getScoreTeam1())
                .scoreTeam2(match.getScoreTeam2())
                .statusTournamentAndMatch(match.getStatusMatch())
                .team1Id(match.getTeam1().getId())
                .team2Id(match.getTeam2().getId())
                .build();
        if (match.getWinnerTeam()!=null){
            matchResponseDtoInProgress.setNameWinnerTeam(match.getWinnerTeam().getName());
            matchResponseDtoInProgress.setWinnerTeamId(match.getWinnerTeam().getId());
        }
        return matchResponseDtoInProgress;
    }

    public  Match fromMatchDtoToMatch(MatchResponseDtoInProgress match){
        Optional<Team> team=teamRepository.findById(match.getTeam1Id());
        Optional<Team> team1=teamRepository.findById(match.getTeam2Id());
        Optional<Team> winnerTeam=teamRepository.findById(match.getWinnerTeamId());
        Match match1=Match.builder()
                .id(match.getMatchId())
                .team1(team.get())
                .team2(team1.get())
                .statusMatch(match.getStatusTournamentAndMatch())
                .scoreTeam1(match.getScoreTeam1())
                .scoreTeam2(match.getScoreTeam2())
                .winnerTeam(winnerTeam.get())
                .build();
        if(match.getWinnerTeamId()!=null){
            match1.setWinnerTeam(winnerTeam.get());
        }
        return match1;
    }



}








