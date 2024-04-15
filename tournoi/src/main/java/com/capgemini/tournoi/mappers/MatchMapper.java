package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.*;
import com.capgemini.tournoi.entity.*;
import com.capgemini.tournoi.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public  MatchResponseDto fromMatch(Match match) {
        List<String> namePlayersTeam1 =new ArrayList<>();
        for(Player player:match.getTeam1().getPlayers()){
            namePlayersTeam1.add(player.getFirstName()+" "+player.getLastName());
        }
        List<String> namePlayersTeam2 =new ArrayList<>();
        for(Player player:match.getTeam2().getPlayers()){
            namePlayersTeam2.add(player.getFirstName()+" "+player.getLastName());
        }
        return MatchResponseDto.builder()
                .id(match.getId())
                .startTime(match.getStartTime())
                .namePlayersTeam1(namePlayersTeam1)
                .namePlayersTeam2(namePlayersTeam2)
                .nameWinnerTeam(match.getWinnerTeam().getName())
                .labelTournament(match.getTournament().getLabel())
                .statusTournamentAndMatch(match.getStatusMatch())
                .nameTeam1(teamMapper.fromTeam(match.getTeam1()))
                .nameTeam2(teamMapper.fromTeam(match.getTeam2()))
                .goals(match.getScore().getGoals())
                .build();
    }

    public MatchResponseDtoInProgress convertToDto(Match match) {
        List<String> namePlayersTeam1 =new ArrayList<>();
        HashMap<String,Integer> hashMap=new HashMap<>();
        for(Player player:match.getTeam1().getPlayers()){
            namePlayersTeam1.add(player.getFirstName()+" "+player.getLastName());
        }
        List<String> namePlayersTeam2 =new ArrayList<>();
        for(Player player:match.getTeam2().getPlayers()){
            namePlayersTeam2.add(player.getFirstName()+" "+player.getLastName());
        }
        List<Goal> goals = match.getScore().getGoals();
        for (Goal goal : goals) {
            for (Player player : match.getTeam1().getPlayers()) {
                if (goal.getPlayer().getId() == player.getId()) {
                    if (hashMap.containsKey("team1")) {
                        hashMap.replace("team1", hashMap.get("team1") + 1);
                    } else {
                        hashMap.put("team1", 1);
                    }
                }
            }
            for (Player player : match.getTeam2().getPlayers()) {
                if (goal.getPlayer().getId() == player.getId()) {
                    if (hashMap.containsKey("team2")) {
                        hashMap.replace("team2", hashMap.get("team2") + 1);
                    } else {
                        hashMap.put("team2", 1);
                    }
                }
            }

        }

        return MatchResponseDtoInProgress.builder()
                .team1Name(match.getTeam1().getName())
                .team2Name(match.getTeam2().getName())
                .startTime(match.getStartTime())
                .playersTeam1(namePlayersTeam1)
                .playersTeam2(namePlayersTeam2)
                .scoreTeam1(hashMap.get("team1"))
                .scoreTeam2(hashMap.get("team2"))
                .nameWinnerTeam(match.getWinnerTeam().getName())
                .statusTournamentAndMatch(match.getStatusMatch())
                .build();
    }



}








