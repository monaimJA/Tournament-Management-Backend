package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTournament;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentDateException;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;
import com.capgemini.tournoi.mappers.TournamentMapper;
import com.capgemini.tournoi.repos.GoalRepository;
import com.capgemini.tournoi.repos.PlayerRepository;
import com.capgemini.tournoi.repos.TeamRepository;
import com.capgemini.tournoi.repos.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TournamentServiceImpl implements TournamentService{


    private final TournamentRepository tournamentRepository;
    private final PlayerRepository playerRepository;
    private final GoalRepository goalRepository;
    private final TeamRepository teamsRepository;
    private final TournamentMapper tournamentMapper;

    public TournamentServiceImpl(TournamentRepository tournamentRepository, PlayerRepository playerRepository, GoalRepository goalRepository, TeamRepository teamsRepository, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
        this.goalRepository = goalRepository;
        this.teamsRepository = teamsRepository;
        this.tournamentMapper = tournamentMapper;
    }

    public Tournament createTournament(CreateTournamentRequestDto tournamentDto) throws TournamentDateException {
        if (tournamentDto.getStartDate().isAfter(tournamentDto.getEndDate())) {
            throw new TournamentDateException("Tournament start date should not come after the end date");
        }
        Tournament tournament = tournamentMapper.fromTournamentDtoRequest(tournamentDto);
        return tournamentRepository.save(tournament);
    }
    public List<TournamentResponseDto> getAllTournaments(){
        return tournamentRepository.findAll().stream()
                .map(tournamentMapper::fromTournament)
                .collect(Collectors.toList());
    }
    public TournamentResponseDto getTournamentById(Long id) throws TournamentNotFoundException {

        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + id + " does not exist"));
        return tournamentMapper.fromTournament(tournament);
    }
    public HashMap<String, Integer> tournamentScorers(Long tournamentId) throws TournamentNotFoundException {
        HashMap<String, Integer> scorers = new HashMap<>();
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        for (Team team : teamsRepository.getTeamsByTournament(tournament)) {
            for (Player player : playerRepository.findAllByTeam(team)) {
                scorers.put(player.getFirstName() + " " + player.getLastName(), goalRepository.findAllByPlayer(player).size());
            }
        }
        return scorers;
    }
    public List<Team> getTeamsByTournamentStatus(Long tournamentId, StatusTournament status) throws TournamentNotFoundException {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        return teamsRepository.getTeamsByTournament_StatusTournament(status);
    }
    public TournamentResponseDto addTeamToTournament(Long tournamentId, Long teamId) throws TeamNotFoundException, TournamentNotFoundException {
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team with Team id = " + teamId + " does not exist"));
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        team.setTournament(tournament);
        tournament.getTeams().add(team);
        teamsRepository.save(team);
        tournamentRepository.save(tournament);
        return tournamentMapper.fromTournament(tournament);
    }
    public TournamentResponseDto deleteTeamFromTournament(Long tournamentId, Long teamId) throws TeamNotFoundException, TournamentNotFoundException {
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team with Team id = " + teamId + " does not exist"));
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        tournament.getTeams().remove(team);
        team.setTournament(null);
        teamsRepository.save(team);
        tournamentRepository.save(tournament);
        return tournamentMapper.fromTournament(tournament);
    }

}
