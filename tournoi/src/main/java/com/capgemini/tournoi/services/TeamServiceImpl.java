package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.mappers.TeamMapper;
import com.capgemini.tournoi.repos.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService{
    TeamRepository teamRepository;

    TeamMapper teamMapper;
    @Override
    public TeamDto saveTeam(TeamDto teamDto) {
        Team team=teamMapper.fromTeamDto(teamDto);
        Team savedTeam = teamRepository.save(team);
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
