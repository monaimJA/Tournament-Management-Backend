package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.MatchRequestDTO;
import com.monaim.tournoi.dtos.MatchResponseDto;
import com.monaim.tournoi.dtos.MatchResponseDtoInProgress;
import com.monaim.tournoi.entity.*;
import com.monaim.tournoi.enums.StatusTeam;
import com.monaim.tournoi.error.MatchNotFoundException;
import com.monaim.tournoi.globalExceptions.TeamNotFoundException;
import com.monaim.tournoi.mappers.MatchMapper;
import com.monaim.tournoi.repos.MatchRepository;
import com.monaim.tournoi.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MatchServiceImpl  implements MatchServiceInterface {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private  MatchMapper matchMapper;


    public Match createMatch(MatchRequestDTO matchRequest) throws TeamNotFoundException {
        Match newMatch = matchMapper.fromMatchDTO(matchRequest);
        newMatch.setStartTime(matchRequest.getStartTime());
        newMatch.setId(matchRequest.getId());
        Team team1 = teamRepository.findById(matchRequest.getTeamId1()).orElseThrow(() -> new TeamNotFoundException("Equipe 1 non trouvée avec l'ID fourni"));
        Team team2 = teamRepository.findById(matchRequest.getTeamId2()).orElseThrow(() -> new TeamNotFoundException("Equipe 2 non trouvée avec l'ID fourni"));
        newMatch.setTeam1(team1);
        newMatch.setTeam2(team2);
        newMatch.setScore(null);
        return matchRepository.save(newMatch);
    }


    public List<MatchResponseDtoInProgress> getAllMatchesF() {
        return matchRepository.findAll().stream()
                .map(match -> {
                   // System.out.println(match.getTeam1().getPlayers());
                   // System.out.println(match);
                    return matchMapper.convertToDto(match);
                })
                .collect(Collectors.toList());
    }





    public MatchResponseDto getMatchById(Long id) throws MatchNotFoundException {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("match with this id " + id + " not exist"));
        return matchMapper.fromMatch(match);

    }


    public Score getMatchScore(Long id) {
        Match match = matchRepository.findById(id).get();
        if (match != null) {
            return match.getScore();
        }
        return null;
    }

    public Match setScoreOfMatch(Long matchId,int scoreTeam1,int scoreTeam2) throws MatchNotFoundException {
        Optional<Match> match = matchRepository.findById(matchId);
        if (match.isPresent()) {
            Match match1 = match.get();
            match1.setScoreTeam1(scoreTeam1);
            match1.setScoreTeam2(scoreTeam2);
            Team winnerTeam = scoreTeam1 > scoreTeam2 ? match1.getTeam1() : match1.getTeam2();
            match1.setWinnerTeam(winnerTeam);
            return matchRepository.save(match1);
        } else {
            throw new MatchNotFoundException("match with id=" + matchId + "does not exist");
        }
    }

    public List<Player> getMatchScorers(Long matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match != null) {
            List<Player> players = new ArrayList<>();
            for (Goal goal : match.getScore().getGoals()) {
                players.add(goal.getPlayer());
            }
            return players;
        }
        return null;
    }

    @Override
    public Match setTeamForfaitInMatch(Long teamId, Long matchId) throws MatchNotFoundException {
        Optional<Match> match = matchRepository.findById(matchId);
        if (match.isPresent()) {
            Team team = match.get().getTeam1().getId() != teamId ? match.get().getTeam1()
                    : match.get().getTeam2();
            Match match1 = match.get();
            match1.setWinnerTeam(team);
            team.setStatusTeam(StatusTeam.FORFAIT);
            return matchRepository.save(match1);
        } else {
            throw new MatchNotFoundException
                    ("match with id=" + matchId + " does not exist");
        }

    }
}

