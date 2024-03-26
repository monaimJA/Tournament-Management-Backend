package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Site;
import com.capgemini.tournoi.entity.Team;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamMapper {
    public TeamDto convertTeamToTeamDto(Team team){
        TeamDto teamDto=new TeamDto();
        teamDto.setId(team.getId());
        teamDto.setName(team.getName());
        teamDto.setSiteName(team.getSite().getName());
        teamDto.setTournamentName(team.getTournament().getLabel());
        teamDto.setStatusTeam(team.getStatusTeam());
        List<String> players=team.getPlayers().stream()
                .map(player->player.getFirstName()+" "+player.getLastName())
                .collect(Collectors.toList());
        teamDto.setPlayers(players);
        teamDto.setStatusTournoi(team.getTournament().getStatusTournament());
        return teamDto;
    }
    public Team convertTeamDtoToTeam(TeamDto teamDto){
        Team team=new Team();
        team.setStatusTeam(teamDto.getStatusTeam());
        team.setId(teamDto.getId());
        Site site=new Site();
        site.setName(teamDto.getSiteName());
        team.setSite(site);
        List<Player> players=teamDto.getPlayers().stream()
                .map(player->{
                    String[] firstAndLastname=player.split(" ");
                    String firstName=firstAndLastname[0];
                    String lastName=firstAndLastname[1];
                    Player player1=new Player();
                    player1.setFirstName(firstName);
                    player1.setLastName(lastName);
                    return player1;
                })
                .collect(Collectors.toList());
        team.setPlayers(players);
        return team;
    }
}
