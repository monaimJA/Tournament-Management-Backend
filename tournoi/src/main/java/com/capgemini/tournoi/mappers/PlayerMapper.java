package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper
{
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
        if(player.getTeam()!=null){
            playerDto.setTeamName(player.getTeam().getName());
        }
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
