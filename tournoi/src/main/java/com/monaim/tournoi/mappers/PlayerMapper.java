package com.monaim.tournoi.mappers;

import com.monaim.tournoi.dtos.PlayerDto;
import com.monaim.tournoi.entity.Player;
import com.monaim.tournoi.entity.Team;
import com.monaim.tournoi.enums.CardType;
import com.monaim.tournoi.repos.CardRepository;
import com.monaim.tournoi.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper
{
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TeamRepository teamRepository;
    public PlayerDto convertPlayerToPlayerDTO(Player player){
        PlayerDto playerDto=new PlayerDto();
        playerDto.setId(player.getId());
        if(player.getFirstName()!=null){
            playerDto.setFirstName(player.getFirstName());
        }
        if (player.getLastName()!=null){
            playerDto.setLastName(player.getLastName());
        }
        if (player.getPhoneNumber()!=null){
            playerDto.setPhoneNumber(player.getPhoneNumber());
        }
        if(player.getPlayerStatus()!=null){
            playerDto.setPlayerStatus(player.getPlayerStatus());
        }
        if(player.getEmail()!=null){
            playerDto.setEmail(player.getEmail());
        }
        if(player.getTeam().getName()!=null){
            playerDto.setTeamName(player.getTeam().getName());
        }
        playerDto.setNumberOfYellowCards(cardRepository.countAllByPlayer_IdAndCardType(player.getId(), CardType.YELLOW_CARD));
        playerDto.setNumberOfRedCards(cardRepository.countAllByPlayer_IdAndCardType(player.getId(),CardType.RED_CARD));
        return playerDto;
    }
    public Player convertPlayerDtoToPlayer(PlayerDto playerDto){
        Player  player=new Player();
        player.setPlayerStatus(playerDto.getPlayerStatus());
        Team team=new Team();
        team.setName(playerDto.getTeamName());
        player.setTeam(team);
        player.setLastName(playerDto.getLastName());
        player.setFirstName(playerDto.getFirstName());
        player.setEmail(playerDto.getEmail());
        player.setPhoneNumber(playerDto.getPhoneNumber());
        player.setId(playerDto.getId());
        return player;
    }
}
