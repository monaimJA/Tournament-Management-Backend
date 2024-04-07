package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTournamentAndMatch;
import com.capgemini.tournoi.error.ChangePlayersOfTeamDuringTournamentException;
import com.capgemini.tournoi.error.PlayerExistInAnotherTeamException;
import com.capgemini.tournoi.error.PlayerNotFoundException;
import com.capgemini.tournoi.globalExceptions.MaximumPlayersLimitException;
import com.capgemini.tournoi.globalExceptions.PlayersNotSufficientException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;
import com.capgemini.tournoi.mappers.PlayerMapper;
import com.capgemini.tournoi.mappers.TeamMapper;
import com.capgemini.tournoi.repos.PlayerRepository;
import com.capgemini.tournoi.repos.TeamRepository;
import com.capgemini.tournoi.repos.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService{

    @Autowired
    PlayerRepository playerRepository;
    TeamRepository teamRepository;
    TournamentRepository tournamentRepository;
    TeamMapper teamMapper;
    PlayerMapper playerMapper;
    @Override
    public TeamDto saveTeam(TeamDto teamDto) throws MaximumPlayersLimitException, PlayersNotSufficientException, PlayerExistInAnotherTeamException {
        Team team=teamMapper.fromTeamDto(teamDto);
        if (team.getPlayers().size() > 8) {
            throw new MaximumPlayersLimitException("too much players");
        }
        if (team.getPlayers().size() < 8 ) {
            throw new PlayersNotSufficientException("players not sufficient");
        }
        for(Player player:team.getPlayers()){
            if(playerRepository.findPlayerByEmail(player.getEmail())==null){
                throw new PlayerExistInAnotherTeamException
                        ("the player with id="+player.getId()+" already exist in another team");
            }
        }
        Team savedTeam = teamRepository.save(team);
        return teamMapper.fromTeam(savedTeam);

    }
    @Transactional
    @Override
    public TeamDto inscription(TeamDto teamDto) throws MaximumPlayersLimitException, PlayersNotSufficientException, PlayerExistInAnotherTeamException {
        Team team=teamMapper.fromTeamDto(teamDto);

        if (team.getPlayers().size() > 8) {
            throw new MaximumPlayersLimitException("too much players");
        }
        if (team.getPlayers().size() < 8 ) {
            throw new PlayersNotSufficientException("players not sufficient");
        }
        for(Player player:team.getPlayers()){
            if(playerRepository.findPlayerByEmail(player.getEmail())==null){
                throw new PlayerExistInAnotherTeamException
                        ("the player with id="+player.getId()+" already exist in another team");
            }
        }
        Team savedTeam = teamRepository.save(team);

        for(PlayerDto player : teamDto.getPlayers()){

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

    @Override
    public TeamDto changeTeamPlayer(Long playerId,PlayerDto playerDto,Long tournamentId) throws PlayerNotFoundException, TournamentNotFoundException, ChangePlayersOfTeamDuringTournamentException {
        Optional<Player> player=playerRepository.findById(playerId);
        if(player.isPresent()){
            Optional<Tournament> tournament=tournamentRepository.findById(tournamentId);
            if(tournament.isPresent()){
                if(tournament.get().getStatusTournament()== StatusTournamentAndMatch.INSCRIPTION){
                    Player returnedPlayer=player.get();
                    returnedPlayer.setLastName(playerDto.getLastName());
                    returnedPlayer.setFirstName(playerDto.getFirstName());
                    returnedPlayer.setPhoneNumber(playerDto.getPhoneNumber());
                    returnedPlayer.setEmail(playerDto.getEmail());
                    return teamMapper.fromTeam(playerRepository.save(returnedPlayer).getTeam());
                }else{
                    throw new ChangePlayersOfTeamDuringTournamentException("you can't change players during tournament");
                }
            }else{
                throw new TournamentNotFoundException("tournament with id="+tournamentId+" does not exist");
            }
        }else{
            throw new PlayerNotFoundException("player with id="+playerId+" does not exist");
        }
    }
}
