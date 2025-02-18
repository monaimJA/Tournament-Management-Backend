package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.CreateTournamentRequestDto;
import com.monaim.tournoi.dtos.ModifyTournamentRequestDto;
import com.monaim.tournoi.dtos.TournamentResponseDto;
import com.monaim.tournoi.entity.Player;
import com.monaim.tournoi.entity.Team;
import com.monaim.tournoi.entity.Tournament;
import com.monaim.tournoi.enums.StatusTournamentAndMatch;
import com.monaim.tournoi.error.TournamentAlreadyInProgressException;
import com.monaim.tournoi.globalExceptions.TeamNotFoundException;
import com.monaim.tournoi.globalExceptions.TournamentDateException;
import com.monaim.tournoi.globalExceptions.TournamentNotFoundException;
import com.monaim.tournoi.mappers.TournamentMapper;
import com.monaim.tournoi.repos.*;
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
    private final CardRepository cardRepository;
    private ScoreRepository scoreRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository, PlayerRepository playerRepository, GoalRepository goalRepository, TeamRepository teamsRepository, CardRepository cardRepository, ScoreRepository scoreRepository) {
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
        this.goalRepository = goalRepository;
        this.teamsRepository = teamsRepository;
        this.cardRepository = cardRepository;
        this.scoreRepository = scoreRepository;
    }

    public Tournament createTournament(CreateTournamentRequestDto tournamentDto) throws TournamentDateException, TournamentAlreadyInProgressException {
        if(tournamentRepository.checkExistTournamentInProgress()==null){
            if (tournamentDto.getStartDate().isAfter(tournamentDto.getEndDate())) {
                throw new TournamentDateException("Tournament start date should not come after the end date");
            }
            Tournament tournament = TournamentMapper.fromTournamentDtoRequest(tournamentDto);
            tournament.setStatusTournament(StatusTournamentAndMatch.INSCRIPTION);
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
        if ((updatedTournament.getStatusTournamentAndMatch().equals(StatusTournamentAndMatch.FINISHED) || (updatedTournament.getStatusTournamentAndMatch().equals(StatusTournamentAndMatch.CANCELLED))))tournament.setInProgress(false);
        return TournamentMapper.fromTournament(tournament);
    }

    @Override
    public void deleteTournament(Long tournamentId) throws TournamentNotFoundException {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        tournament.setInProgress(false);
        tournament.setStatusTournament(StatusTournamentAndMatch.CANCELLED);
        tournamentRepository.save(tournament);
    }



    @Override
    public Tournament getCurrentTournament() {
        return tournamentRepository.findByInProgressTrue();
    }
}
