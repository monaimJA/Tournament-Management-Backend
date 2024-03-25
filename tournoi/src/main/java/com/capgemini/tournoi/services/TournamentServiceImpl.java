package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.dtos.TournamentResponseDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTournoi;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentDateException;
import com.capgemini.tournoi.mappers.TournamentMapper;
import com.capgemini.tournoi.repos.GoalRepository;
import com.capgemini.tournoi.repos.PlayerRepository;
import com.capgemini.tournoi.repos.TeamRepository;
import com.capgemini.tournoi.repos.TournoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TournamentServiceImpl implements TournamentService{

    @Autowired
    private TournoiRepository tournoiRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private TeamRepository teamsRepository;
    @Autowired
    private TournamentMapper tournamentMapper;

    public Tournament createTournament(CreateTournamentRequestDto tournamentDto) throws TournamentDateException {
        if (tournamentDto.getStartDate().after(tournamentDto.getEndDate())) {
            throw new TournamentDateException("Tournament start date should not come after the end date");
        }
        Tournament tournament = tournamentMapper.fromTournamentDtoRequest(tournamentDto);
        return tournoiRepository.save(tournament);
    }
    public List<TournamentResponseDto> getAllTournaments(){
        List<TournamentResponseDto> tournaments = tournoiRepository.findAll().stream()
                .map(tournoi -> tournamentMapper.fromTournament(tournoi))
                .collect(Collectors.toList());
        return tournaments;
    }
    public TournamentResponseDto getTournamentById(Long id) throws TournamentNotFoundException {

        Tournament tournament = tournoiRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + id + " does not exist"));
        return tournamentMapper.fromTournament(tournament);
    }
    public HashMap<String, Integer> tournamentScorers(Long tournamentId) throws TournamentNotFoundException {
        HashMap<String, Integer> scorers = new HashMap<>();
        Tournament tournament = tournoiRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        for (Player player : playerRepository.findAll()) {
            scorers.put(player.getFirstName()+" "+player.getLastName(), goalRepository.findAllByPlayer(player).size());
        }
        return scorers;
    }
    public List<Team> getTeamsByTournamentStatus(Long tournamentId, StatusTournoi status) throws TournamentNotFoundException {
        Tournament tournament = tournoiRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        return teamsRepository.getTeamsByTournoi_StatusTournoi(status);
    }
    public TournamentResponseDto addTeamToTournament(Long tournamentId, Long teamId) throws TeamNotFoundException, TournamentNotFoundException {
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team with Team id = " + teamId + " does not exist"));
        Tournament tournament = tournoiRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        team.setTournament(tournament);
        tournament.getTeams().add(team);
        teamsRepository.save(team);
        tournoiRepository.save(tournament);
        return tournamentMapper.fromTournament(tournament);
    }
    public TournamentResponseDto deleteTeamFromTournament(Long tournamentId, Long teamId) throws TeamNotFoundException, TournamentNotFoundException {
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team with Team id = " + teamId + " does not exist"));
        Tournament tournament = tournoiRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        team.setTournament(tournament);
        tournament.getTeams().add(team);
        teamsRepository.save(team);
        tournoiRepository.save(tournament);
        return tournamentMapper.fromTournament(tournament);
    }

}
