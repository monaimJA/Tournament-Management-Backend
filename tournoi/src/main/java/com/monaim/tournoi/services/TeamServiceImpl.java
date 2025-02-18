package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.TeamDto;
import com.monaim.tournoi.dtos.TeamGetDto;
import com.monaim.tournoi.entity.Player;
import com.monaim.tournoi.entity.Site;
import com.monaim.tournoi.entity.Team;
import com.monaim.tournoi.entity.Tournament;
import com.monaim.tournoi.globalExceptions.MaximumPlayersLimitException;
import com.monaim.tournoi.globalExceptions.PlayersNotSufficientException;
import com.monaim.tournoi.globalExceptions.TeamNotFoundException;
import com.monaim.tournoi.globalExceptions.TwoTeamsPlayerException;
import com.monaim.tournoi.mappers.TeamMapper;
import com.monaim.tournoi.repos.PlayerRepository;
import com.monaim.tournoi.repos.SiteRepository;
import com.monaim.tournoi.repos.TeamRepository;
import com.monaim.tournoi.repos.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService{
    TeamRepository teamRepository;
    TeamMapper teamMapper;
    PlayerRepository playerRepository;
    TournamentRepository tournamentRepository;
    SiteRepository siteRepository;

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


    @Transactional
    @Override
    public TeamDto inscription(TeamDto teamDto) throws MaximumPlayersLimitException, PlayersNotSufficientException, TwoTeamsPlayerException {
        Team team=teamMapper.fromTeamDto(teamDto);

        if (team.getPlayers().size() > 8) {
            throw new MaximumPlayersLimitException("too much players");
        }
        if (team.getPlayers().size() < 7 ) {
            throw new PlayersNotSufficientException("players not sufficient");
        }

        try {
            Tournament tournament = tournamentRepository.findByInProgressTrue();
            team.setTournament(tournament);
        } catch (Exception e) {
            throw new RuntimeException("0 or more than 1 tournament is current");
        }



        Team savedTeam = teamRepository.save(team);

//        List<Player> playersInTournament = playerRepository.getAllPlayersOfATournament(teamDto.getTournament().getId());
//        for(Player player : teamDto.getPlayers()){
//            for(Player tournamentPlayer : playersInTournament){
//                if(player.getEmail().equals(tournamentPlayer.getEmail())){
//                    throw new TwoTeamsPlayerException("player already exist in a team");
//                }
//            }
//               }

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
    public List<TeamGetDto> teamsListInTournament(Long tournamentId) {
        List<Team> teams = teamRepository.findByTournamentId(tournamentId);
        List<TeamGetDto> teamList= teams.stream()
                .map(team -> teamMapper.convertTeamToTeamGet(team))
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

    @Override
    public List<Site> getSites() {
        return siteRepository.findAll();
    }


}
