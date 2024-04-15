package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.dtos.MatchResponseDto;
import com.capgemini.tournoi.dtos.MatchResponseDtoInProgress;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Score;
import com.capgemini.tournoi.error.MatchNotFoundException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;

import java.util.List;

public interface MatchServiceInterface {
  Match createMatch(MatchRequestDTO matchRequest) throws TeamNotFoundException;
  List<MatchResponseDtoInProgress> getAllMatchesF();

  MatchResponseDto getMatchById(Long id) throws MatchNotFoundException;
  Score getMatchScore(Long id);

  public Match setScoreOfMatch(Score score,Long matchId) throws MatchNotFoundException;

  public Match setTeamForfaitInMatch(Long teamId,Long matchId) throws MatchNotFoundException;

  List<MatchResponseDtoInProgress> getAllMatchesInLatestPhase();

  public List<MatchResponseDtoInProgress> getMatchesInProgress();
}
