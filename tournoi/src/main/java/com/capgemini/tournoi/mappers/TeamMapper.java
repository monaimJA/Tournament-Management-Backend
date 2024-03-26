package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Team;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

}
