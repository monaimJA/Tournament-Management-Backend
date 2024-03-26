package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Score;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.error.MatchNotFoundException;
import com.capgemini.tournoi.error.ScoreNotFoundException;
import com.capgemini.tournoi.mappers.MatchMapper;
import com.capgemini.tournoi.repos.MatchRepository;
import com.capgemini.tournoi.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MatchServiceImpl  implements MatchServiceInterface{
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchMapper matchMapper;
    public Match createMatch(MatchRequestDTO matchRequest) {
        Match newMatch = matchMapper.fromMatchDTO(matchRequest);
        newMatch.setStartTime(matchRequest.getStartTime());
        Team team1= teamRepository.findById(matchRequest.getTeamId1()).get();
        Team team2 = teamRepository.findById(matchRequest.getTeamId1()).get();
        //List<Team> teams=new ArrayList<>();
        //teams.add(team1);
        //teams.add(team2);
        newMatch.setTeam1(team1);
        newMatch.setTeam2(team2);
        return matchRepository.save(newMatch);
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Long id) throws MatchNotFoundException {
        Optional<Match> match = matchRepository.findById(id);
        if(match.isPresent()){
            return match.get();
        }else{
            throw new MatchNotFoundException("le match avec ce Id" +id+ "n'existe pas");
        }

    }


    public Score getMatchScore(Long id) {
        Match match = matchRepository.findById(id).get();
        if (match != null) {
            return match.getScore();
        }
        return null;
    }


    public List<Player> getMatchScorers(Long matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match != null) {
            return match.getScorers();
        }
        return null;
    }
}

