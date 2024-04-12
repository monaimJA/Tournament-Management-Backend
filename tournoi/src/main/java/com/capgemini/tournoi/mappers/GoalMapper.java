package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.GoalDto;
import com.capgemini.tournoi.entity.Goal;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.repos.MatchRepository;
import com.capgemini.tournoi.repos.PlayerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
@Service
public class GoalMapper {
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;

    public GoalMapper(PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    public GoalDto convertToDto(Goal goal) {
        if (goal == null) {
            return null;
        }

        GoalDto goalDto = new GoalDto();
        goalDto.setId(goal.getId());
        goalDto.setMinute(goal.getMinute());

        if (goal.getPlayer() != null) {
            goalDto.setPlayerId(goal.getPlayer().getId());
        }

        if (goal.getMatch() != null) {
            goalDto.setMatchId(goal.getMatch().getId());
        }

        return goalDto;
    }

    public Goal convertToEntity(GoalDto goalDto) throws EntityNotFoundException {
        if (goalDto == null) {
            return null;
        }

        Goal goal = new Goal();
        goal.setId(goalDto.getId());
        goal.setMinute(goalDto.getMinute());

        if (goalDto.getPlayerId() != null) {
            Player player = playerRepository.findById(goalDto.getPlayerId())
                    .orElseThrow(() -> new EntityNotFoundException("Player not found with ID: " + goalDto.getPlayerId()));
            goal.setPlayer(player);
        }

        if (goalDto.getMatchId() != null) {
            Match match = matchRepository.findById(goalDto.getMatchId())
                    .orElseThrow(() -> new EntityNotFoundException("Match not found with ID: " + goalDto.getMatchId()));
            goal.setMatch(match);
        }

        return goal;
    }
}
