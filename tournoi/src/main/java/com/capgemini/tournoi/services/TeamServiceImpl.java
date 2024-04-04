package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.PlayerInscriptionDto;
import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.globalExceptions.MaximumPlayersLimitException;
import com.capgemini.tournoi.globalExceptions.PlayersNotSufficientException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.mappers.TeamMapper;
import com.capgemini.tournoi.repos.PlayerRepository;
import com.capgemini.tournoi.repos.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService{
    TeamRepository teamRepository;

    TeamMapper teamMapper;
    @Override
    public TeamDto saveTeam(TeamDto teamDto) throws MaximumPlayersLimitException, PlayersNotSufficientException {
        Team team=teamMapper.fromTeamDto(teamDto);
        if (team.getPlayers().size() > 8) {
            throw new MaximumPlayersLimitException("too much players");
        }
        if (team.getPlayers().size() < 8 ) {
            throw new PlayersNotSufficientException("players not sufficient");
        }
        Team savedTeam = teamRepository.save(team);
        return teamMapper.fromTeam(savedTeam);

    }

    @Autowired
    PlayerRepository playerRepository;
    @Transactional
    @Override
    public TeamDto inscription(TeamDto teamDto) throws MaximumPlayersLimitException, PlayersNotSufficientException {
        Team team=teamMapper.fromTeamDto(teamDto);

        if (team.getPlayers().size() > 8) {
            throw new MaximumPlayersLimitException("too much players");
        }
        if (team.getPlayers().size() < 8 ) {
            throw new PlayersNotSufficientException("players not sufficient");
        }
        Team savedTeam = teamRepository.save(team);

        for(Player player : teamDto.getPlayers()){
            Player player1 =new Player();
            player1.setFirstName(player.getFirstName());
            player1.setLastName(player.getLastName());
            player1.setEmail(player.getEmail());
            player1.setPhoneNumber(player.getPhoneNumber());
            player1.setTeam(team);
            playerRepository.save(player1);
        }

        return teamMapper.fromTeam(savedTeam);
    }

    @Override
    public List<TeamDto> teamsList() {
        List<Team> teams = teamRepository.findAll();
        List<TeamDto> teamList= teams.stream()
                .map(team -> teamMapper.fromTeam(team))
                .collect(Collectors.toList());
        return teamList;
    }

    @Override
    public List<TeamDto> teamsListInTournament(Long tournamentId) {
        List<Team> teams = teamRepository.findByTournamentId(tournamentId);
        List<TeamDto> teamList= teams.stream()
                .map(team -> teamMapper.fromTeam(team))
                .collect(Collectors.toList());
        return teamList;
    }

    @Override
    public TeamDto getTeam(Long id) throws TeamNotFoundException {
        Team team = teamRepository.findById(id)
                .orElseThrow(()-> new TeamNotFoundException("Team not found"));
        return teamMapper.fromTeam(team);
    }

    @Override
    public TeamDto updateStatus(Long id,TeamDto teamDto) {
        Team team=teamRepository.getById(id);
        team.setStatusTeam(teamDto.getStatusTeam());
        teamRepository.save(team);

        return teamMapper.fromTeam(team);
    }


}
