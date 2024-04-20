package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.MatchRequestDTO;
import com.monaim.tournoi.dtos.MatchResponseDto;
import com.monaim.tournoi.dtos.MatchResponseDtoInProgress;
import com.monaim.tournoi.entity.Match;
import com.monaim.tournoi.entity.Score;
import com.monaim.tournoi.error.MatchNotFoundException;
import com.monaim.tournoi.globalExceptions.TeamNotFoundException;

import java.util.List;

public interface MatchServiceInterface {
  Match createMatch(MatchRequestDTO matchRequest) throws TeamNotFoundException;
  List<MatchResponseDtoInProgress> getAllMatchesF();

  MatchResponseDto getMatchById(Long id) throws MatchNotFoundException;
  Score getMatchScore(Long id);

  Match setScoreOfMatch(Long matchId,int scoreTeam1,int scoreTeam2) throws MatchNotFoundException;

 Match setTeamForfaitInMatch(Long teamId,Long matchId) throws MatchNotFoundException;
}
