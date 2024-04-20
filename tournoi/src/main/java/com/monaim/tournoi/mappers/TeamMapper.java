package com.monaim.tournoi.mappers;

import com.monaim.tournoi.dtos.PlayerTeamDto;
import com.monaim.tournoi.dtos.TeamDto;
import com.monaim.tournoi.dtos.TeamGetDto;
import com.monaim.tournoi.entity.Player;
import com.monaim.tournoi.entity.Team;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamMapper {

    public TeamDto fromTeam(Team team){
        TeamDto teamDto=new TeamDto();
        BeanUtils.copyProperties(team,teamDto);
        return  teamDto;
    }
    public Team fromTeamDto(TeamDto teamDto){
        Team team=new Team();
        BeanUtils.copyProperties(teamDto,team);
        return  team;
    }
    public TeamGetDto convertTeamToTeamGet(Team team){
        TeamGetDto teamGetDto=new TeamGetDto();
        BeanUtils.copyProperties(team,teamGetDto);
        teamGetDto.setPlayers(mapPlayersToDto(team.getPlayers()));

        return  teamGetDto;
    }

    private List<PlayerTeamDto> mapPlayersToDto(List<Player> players) {
        return players.stream()
                .map(player -> {
                    PlayerTeamDto playerDto = new PlayerTeamDto();
                    playerDto.setId(player.getId());
                    playerDto.setFirstName(player.getFirstName());
                    playerDto.setLastName(player.getLastName());
                    playerDto.setEmail(player.getEmail());
                    playerDto.setPhoneNumber(player.getPhoneNumber());
                    return playerDto;
                })
                .collect(Collectors.toList());
    }
}
