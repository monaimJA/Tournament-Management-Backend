package com.capgemini.tournoi.controllers;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.dtos.MatchResponseDto;
import com.capgemini.tournoi.dtos.MatchResponseDtoInProgress;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Score;
import com.capgemini.tournoi.error.MatchNotFoundException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.mappers.MatchMapper;
import com.capgemini.tournoi.repos.MatchRepository;
import com.capgemini.tournoi.services.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/match")
public class MatchController {
    @Autowired
    private MatchServiceImpl matchServiceImpl;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchMapper matchMapper;

    @PostMapping("/create")
    public ResponseEntity<Match> createMatch(@RequestBody MatchRequestDTO matchDOT) throws TeamNotFoundException {
        Match match = matchServiceImpl.createMatch(matchDOT);
        return ResponseEntity.ok(match);
    }

    @PostMapping("/score/{matchId}")
    public ResponseEntity<Match> setScoreOfMatch(@PathVariable Long matchId,int scoreTeam1,int scoreTeam2) throws MatchNotFoundException {
        Match match = matchServiceImpl.setScoreOfMatch(matchId,scoreTeam1,scoreTeam2);
        return ResponseEntity.ok(match);
    }

    @GetMapping("/{matchId}/team/{teamId}/forfait")
    public ResponseEntity<Match> setTeamForfaitInMatch(@PathVariable Long teamId,@PathVariable Long matchId) throws MatchNotFoundException {
        Match match=matchServiceImpl.setTeamForfaitInMatch(teamId,matchId);
        return ResponseEntity.ok(match);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDto> getMatchById(@PathVariable Long id) {
        try {
            MatchResponseDto matchResponseDto = matchServiceImpl.getMatchById(id);
            return ResponseEntity.ok(matchResponseDto);
        } catch (MatchNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND," Match not found with id " + id);
        }
    }


    @GetMapping("/{id}/score")
    public ResponseEntity<Score> getMatchScore(@PathVariable Long id) {
        Score score = matchServiceImpl.getMatchScore(id);
        return ResponseEntity.ok(score);
    }



    @GetMapping("/{id}/scorers")
    public ResponseEntity<List<Player>> getMatchScorers(@PathVariable Long id) {
        List<Player> scorers = matchServiceImpl.getMatchScorers(id);
        if (scorers != null) {
            return ResponseEntity.ok(scorers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/in-progress")
    public ResponseEntity<List<MatchResponseDtoInProgress>> getAllMatchesOfCurrentTournament() {
        List<MatchResponseDtoInProgress> matches=matchRepository.getAllMatchesInCurrentTournament().stream().map(match->{
            return matchMapper.convertToDto(match);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(matches,HttpStatus.OK);
    }
    @GetMapping("/in-progress/latest")
    public ResponseEntity<List<MatchResponseDtoInProgress>> getLatestMatches(){
        List<MatchResponseDtoInProgress> matches=matchRepository.getAllLatestMatchesInCurrentTournament().stream().map(match->{
            return matchMapper.convertToDto(match);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(matches,HttpStatus.OK);
    }
}
