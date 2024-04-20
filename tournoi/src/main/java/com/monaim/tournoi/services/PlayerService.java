package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.PlayerDto;
import com.monaim.tournoi.dtos.PlayersCardsDto;
import com.monaim.tournoi.dtos.ScorersResponseDto;
import com.monaim.tournoi.dtos.TeamDto;
import com.monaim.tournoi.entity.Match;
import com.monaim.tournoi.entity.Player;
import com.monaim.tournoi.enums.CardType;
import com.monaim.tournoi.enums.StatusTournamentAndMatch;
import com.monaim.tournoi.error.PlayerNotFoundException;
import com.monaim.tournoi.globalExceptions.TeamNotFoundException;
import com.monaim.tournoi.globalExceptions.TournamentNotFoundException;

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

    public List<PlayersCardsDto> getPlayersWithCardsNumber();
}
