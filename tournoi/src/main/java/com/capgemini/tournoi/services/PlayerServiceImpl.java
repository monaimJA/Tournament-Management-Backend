package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.CardType;
import com.capgemini.tournoi.enums.PlayerStatus;
import com.capgemini.tournoi.error.PlayerNotFoundException;
import com.capgemini.tournoi.mappers.PlayerMapper;
import com.capgemini.tournoi.mappers.TeamMapper;
import com.capgemini.tournoi.repos.MatchRepository;
import com.capgemini.tournoi.repos.PlayerRepository;
import com.capgemini.tournoi.repos.TeamRepository;
import com.capgemini.tournoi.repos.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    PlayerMapper playerMapper;
    @Autowired
    TeamMapper teamMapper;

    @Override
    public List<PlayerDto> getAllPlayersOfATeam(long id) {
        List<Player> players=playerRepository.findAllByTeam_Id(id);
        return players.stream().map(player -> playerMapper.convertPlayerToPlayerDTO(player)).collect(Collectors.toList());
    }

    @Override
    public PlayerDto getPlayerById(long id) throws PlayerNotFoundException {
        Optional<Player> player=playerRepository.findById(id);
        if(player.isPresent()){
            return playerMapper.convertPlayerToPlayerDTO(player.get());
        }else{
            throw new PlayerNotFoundException("player with id"+id+"doesn't exit");
        }
    }

    @Override
    public PlayerDto assignPlayerToTeam(Player player, long teamId) {
        Optional<Team> team=teamRepository.findById(teamId);
        if(team.isPresent())
        {
            player.setPlayerStatus(PlayerStatus.INSCRIT);
            player.setTeam(team.get());
            return playerMapper.convertPlayerToPlayerDTO(playerRepository.save(player));
        }else{
            throw new RuntimeException("team with id="+teamId+"doesn't exist");
        }
    }

    @Transactional
    @Override
    public TeamDto deletePlayerByIdFromTeam(long playerId,long teamId) throws PlayerNotFoundException {
        Optional<Team> team=teamRepository.findById(teamId);
        if(team.isPresent()){
            Team team1=team.get();
            List<Player> players=playerRepository.findAllByTeam_Id(teamId);
            Optional<Long> id=players.stream().filter(player -> player.getId()==playerId).findFirst().map(Player::getId);
                if(id.isPresent()){
                    playerRepository.deletePLayerInTeam(playerId);
                }else {
                    throw new PlayerNotFoundException("player with id"+playerId+
                            "doesn't exit");
                }
            return teamMapper.convertTeamToTeamDto(team1);
        }else {
            throw new RuntimeException("team with id doesn't exist");
        }
    }

    @Override
    public PlayerDto updatePlayerById(Player player, long id) {
        Optional<Player> optionalPlayer=playerRepository.findById(id);
        if (optionalPlayer.isPresent()){
            Player player1=optionalPlayer.get();
            if(player.getFirstName()!=null){
                player1.setFirstName(player.getFirstName());
            }
            if (player.getLastName()!=null){
                player1.setLastName(player.getLastName());
            }
            if (player.getPhoneNumber()!=null){
                player1.setPhoneNumber(player.getPhoneNumber());
            }
            if(player.getPlayerStatus()!=null){
                player1.setPlayerStatus(player.getPlayerStatus());
            }
            if(player.getEmail()!=null){
                player1.setEmail(player.getEmail());
            }
            if(player.getTeam()!=null){
                player1.setTeam(player.getTeam());
            }
            if(player.getCards()!=null){
                player1.setCards(player.getCards());
            }
            return playerMapper.convertPlayerToPlayerDTO(playerRepository.save(player1));
        }
        else {
            throw new RuntimeException("player with id="+id+"doesn't exist");
        }
    }
    @Override
    public List<PlayerDto> getPlayersInTournoiByCardType(CardType cardType, long tournoiId) {
        List<Player> players=playerRepository.getAllInTournoiWithCard(cardType.ordinal(), tournoiId);
        return players.stream().map(player->playerMapper.convertPlayerToPlayerDTO(player))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerDto> getPlayersInMatchByCardType(long matchId, CardType cardType) {
        List<Player> players=playerRepository.getAllInMatchWithCard(cardType.ordinal(), matchId);
        return players.stream().map(player->playerMapper.convertPlayerToPlayerDTO(player))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerDto> getAllPlayersOfTournament(long tournament_id) {
        List<Player> players=playerRepository.getAllPlayersOfATournament(tournament_id);
        return players.stream().map(player->playerMapper.convertPlayerToPlayerDTO(player))
                .collect(Collectors.toList());
    }
}
