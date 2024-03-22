package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.CreateTournamentRequestDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournoi;
import com.capgemini.tournoi.enums.StatusTournoi;
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

    public Tournoi createTournament(CreateTournamentRequestDto tournamentDto) throws TournamentDateException {
        if (tournamentDto.getStartDate().after(tournamentDto.getEndDate())) {
            throw new TournamentDateException("Tournament start date should not come after the end date");
        }
        Tournoi tournament = tournamentMapper.fromTournamentDtoRequest(tournamentDto);
        return tournoiRepository.save(tournament);
    }
    public List<Tournoi> getAllTournaments(){
        return tournoiRepository.findAll();
    }
    public Tournoi getTournamentById(Long id) throws TournamentNotFoundException {
        return tournoiRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + id + " does not exist"));
    }
    public HashMap<String, Integer> tournamentScorers(Long tournamentId) throws TournamentNotFoundException {
        HashMap<String, Integer> scorers = new HashMap<>();
        Tournoi tournament = tournoiRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        for (Player player : playerRepository.findAll()) {
            scorers.put(player.getFirstName()+" "+player.getLastName(), goalRepository.findAllByPlayer(player).size());
        }
        return scorers;
    }
    public List<Team> getTeamsByTournamentStatus(Long tournamentId, StatusTournoi status) throws TournamentNotFoundException {
        Tournoi tournament = tournoiRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with id " + tournamentId + " does not exist"));
        return teamsRepository.getTeamsByTournoi_StatusTournoi(status);
    }

}
