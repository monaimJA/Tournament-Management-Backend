package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.dtos.ModifyTournamentRequestDto;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.error.TournamentAlreadyInProgressException;
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

    public TournamentServiceImpl(TournamentRepository tournamentRepository, PlayerRepository playerRepository, GoalRepository goalRepository, TeamRepository teamsRepository) {
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
        this.goalRepository = goalRepository;
        this.teamsRepository = teamsRepository;
    }

    public Tournament createTournament(CreateTournamentRequestDto tournamentDto) throws TournamentDateException, TournamentAlreadyInProgressException {
        if(tournamentRepository.checkExistTournamentInProgress()==null){
            if (tournamentDto.getStartDate().isAfter(tournamentDto.getEndDate())) {
                throw new TournamentDateException("Tournament start date should not come after the end date");
            }
            Tournament tournament = TournamentMapper.fromTournamentDtoRequest(tournamentDto);
            return tournamentRepository.save(tournament);
        }else {
            throw new TournamentAlreadyInProgressException("you can't create a tournament " +
                    "because there is already a tournament in progress");
        }

    }
    public List<TournamentResponseDto> getAllTournaments(){
        return tournamentRepository.findAll().stream()
                .map(TournamentMapper::fromTournament)
                .collect(Collectors.toList());
    }
    public TournamentResponseDto getTournamentById(Long id) throws TournamentNotFoundException {

        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + id + " does not exist"));
        return TournamentMapper.fromTournament(tournament);
    }
    public HashMap<String, Integer> tournamentScorers(Long tournamentId) throws TournamentNotFoundException {
        HashMap<String, Integer> scorers = new HashMap<>();
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        for (Team team : teamsRepository.findByTournamentId(tournamentId)) {
            for (Player player : playerRepository.findAllByTeam(team)) {
                scorers.put(player.getFirstName() + " " + player.getLastName(), goalRepository.findAllByPlayer(player).size());
            }
        }
        return scorers;
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
        return TournamentMapper.fromTournament(tournament);
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
        return TournamentMapper.fromTournament(tournament);
    }
    @Override
    public TournamentResponseDto modifyTournament(Long tournamentId, ModifyTournamentRequestDto updatedTournament) throws TournamentNotFoundException {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        tournament.setLabel(updatedTournament.getLabel() != null ? updatedTournament.getLabel() : tournament.getLabel());
        tournament.setStatusTournament(updatedTournament.getStatusTournamentAndMatch() != null ? updatedTournament.getStatusTournamentAndMatch() : tournament.getStatusTournament());
        tournament.setStartDate(updatedTournament.getStartDate() != null ? updatedTournament.getStartDate() : tournament.getStartDate());
        tournament.setEndDate(updatedTournament.getEndDate() != null ? updatedTournament.getEndDate() : tournament.getEndDate());
        return TournamentMapper.fromTournament(tournament);
    }

    @Override
    public Tournament getCurrentTournament() {
        return tournamentRepository.findByInProgressTrue();
    }
}
