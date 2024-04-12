package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.dtos.MatchResponseDto;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.*;
import com.capgemini.tournoi.enums.StatusTeam;
import com.capgemini.tournoi.error.MatchNotFoundException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.mappers.MatchMapper;
import com.capgemini.tournoi.mappers.TournamentMapper;
import com.capgemini.tournoi.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class MatchServiceImpl  implements MatchServiceInterface{

    private MatchMapper matchMapper;


    private final MatchRepository matchRepository;
    private final TeamRepository teamsRepository;
    public MatchServiceImpl(MatchRepository matchRepository, TeamRepository teamsRepository) {
        this.matchRepository = matchRepository;
        this.teamsRepository = teamsRepository;
    }

    public Match createMatch(MatchRequestDTO matchRequest) throws TeamNotFoundException {
        Match newMatch = matchMapper.fromMatchDTO(matchRequest);
        newMatch.setStartTime(matchRequest.getStartTime());
        newMatch.setId(matchRequest.getId());
        Team team1= teamsRepository.findById(matchRequest.getTeamId1()).orElseThrow(() -> new TeamNotFoundException("Equipe 1 non trouvée avec l'ID fourni"));
        Team team2 = teamsRepository.findById(matchRequest.getTeamId1()).orElseThrow(() -> new TeamNotFoundException("Equipe 2 non trouvée avec l'ID fourni"));
        newMatch.setTeam1(team1);
        newMatch.setTeam2(team2);
        newMatch.setScore(null);
        return matchRepository.save(newMatch);
    }


   public List<MatchResponseDto> getAllMatches(){
       return matchRepository.findAll().stream()
               .map(MatchMapper::fromMatch)
               .collect(Collectors.toList());
   }


    public MatchResponseDto getMatchById(Long id) throws MatchNotFoundException {
        Match match= matchRepository.findById(id)
                .orElseThrow(()-> new MatchNotFoundException("match with this id " +id + " not exist"));
        return  MatchMapper.fromMatch(match);

    }


    public Score getMatchScore(Long id) {
        Match match = matchRepository.findById(id).get();
        if (match != null) {
            return match.getScore();
        }
        return null;
    }

    public Match setScoreOfMatch(Score score,Long matchId) throws MatchNotFoundException {
        Optional<Match> match=matchRepository.findById(matchId);
        if (match.isPresent()){
            Match match1=match.get();
            match1.setScore(score);
            Map<String,Integer> resultOfMatch=new HashMap<>();
            List<Goal> goals=score.getGoals();
            for(Goal goal:goals){
                for (Player player:match1.getTeam1().getPlayers()){
                    if(goal.getPlayer().getId()== player.getId()){
                        if (resultOfMatch.containsKey("team1")){
                            resultOfMatch.replace("team1",resultOfMatch.get("team1")+1);
                        }else {
                            resultOfMatch.put("team1",1);
                        }
                    }
                }
                for (Player player:match1.getTeam2().getPlayers()){
                    if(goal.getPlayer().getId()== player.getId()){
                        if (resultOfMatch.containsKey("team2")){
                            resultOfMatch.replace("team2",resultOfMatch.get("team2")+1);
                        }else {
                            resultOfMatch.put("team2",1);
                        }
                    }
                }
            }
            Team winnerTeam=resultOfMatch.get("team1")>resultOfMatch.get("team2")?match1.getTeam1():match1.getTeam2();
            match1.setWinnerTeam(winnerTeam);
            return matchRepository.save(match1);
        }else {
            throw new MatchNotFoundException("match with id="+matchId+"does not exist");
        }
    }
    public List<Player> getMatchScorers(Long matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match != null) {
            List<Player> players=new ArrayList<>();
            for (Goal goal:match.getGoals()){
                players.add(goal.getPlayer());
            }
            return players;
        }
        return null;
    }

    @Override
    public Match setTeamForfaitInMatch(Long teamId, Long matchId) throws MatchNotFoundException {
        Optional<Match> match=matchRepository.findById(matchId);
        if(match.isPresent()){
            Team team=match.get().getTeam1().getId()!=teamId?match.get().getTeam1()
                    : match.get().getTeam2();
            Match match1=match.get();
            match1.setWinnerTeam(team);
            team.setStatusTeam(StatusTeam.FORFAIT);
            return matchRepository.save(match1);
        }else{
            throw new MatchNotFoundException
                    ("match with id="+matchId+" does not exist");
        }

    }
}

