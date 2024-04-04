package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Score;
import com.capgemini.tournoi.error.MatchNotFoundException;

import java.util.List;

public interface MatchServiceInterface {
  Match createMatch(MatchRequestDTO matchRequest);
  List<Match> getAllMatches();

  Match getMatchById(Long id) throws MatchNotFoundException;
  Score getMatchScore(Long id);

}
