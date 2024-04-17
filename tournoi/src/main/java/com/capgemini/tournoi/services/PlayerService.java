package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.dtos.PlayersCardsDto;
import com.capgemini.tournoi.dtos.ScorersResponseDto;
import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.enums.CardType;
import com.capgemini.tournoi.enums.StatusTournamentAndMatch;
import com.capgemini.tournoi.error.PlayerNotFoundException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PlayerService {
    public List<PlayerDto> getAllPlayersOfATeam(long teamId);
    public PlayerDto getPlayerById(long id) throws PlayerNotFoundException;
    public PlayerDto assignPlayerToTeam(Player player, long teamId);
    public TeamDto deletePlayerByIdFromTeam(long playerId,long teamId) throws PlayerNotFoundException;
    public PlayerDto updatePlayerById(PlayerDto playerDto,long id);
    public List<PlayerDto> getPlayersInTournoiByCardType
            (CardType cardType,long tournoiId);
    public List<PlayerDto> getPlayersInMatchByCardType(long matchId,CardType cardType);

    public List<PlayerDto> getAllPlayersOfTournament(long tournament_id);

    public List<Match> notifyPlayers(long tournament_id, StatusTournamentAndMatch statusTournamentAndMatch) throws TeamNotFoundException, TournamentNotFoundException;
    public List<Match> getAllMatchesOfTournamentInThatPhase(Long tournamentId, StatusTournamentAndMatch statusTournamentAndMatch);
    public List<ScorersResponseDto> getTopScorers();

    public List<PlayersCardsDto> getPlayersWithCardsNuber();
}
