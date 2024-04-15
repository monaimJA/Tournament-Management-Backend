package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.*;
import com.capgemini.tournoi.error.PlayerNotFoundException;
import com.capgemini.tournoi.globalExceptions.TeamNotFoundException;
import com.capgemini.tournoi.globalExceptions.TournamentNotFoundException;
import com.capgemini.tournoi.mappers.PlayerMapper;
import com.capgemini.tournoi.mappers.TeamMapper;
import com.capgemini.tournoi.repos.MatchRepository;
import com.capgemini.tournoi.repos.PlayerRepository;
import com.capgemini.tournoi.repos.TeamRepository;
import com.capgemini.tournoi.repos.TournamentRepository;
import com.capgemini.tournoi.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private MatchServiceImpl matchService;

    @Autowired
    private TirageService tirageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    PlayerMapper playerMapper;
    @Autowired
    TeamMapper teamMapper;

    @Override
    public List<PlayerDto> getAllPlayersOfATeam(long id) {
        List<Player> players=playerRepository.findAllByTeam_Id(id);
        return players.stream().map(player -> playerMapper.convertPlayerToPlayerDTO(player)).collect(Collectors.toList());
    }

    @Override
    public PlayerDto getPlayerById(long id) throws PlayerNotFoundException {
        Optional<Player> player=playerRepository.findById(id);
        if(player.isPresent()){
            return playerMapper.convertPlayerToPlayerDTO(player.get());
        }else{
            throw new PlayerNotFoundException("player with id"+id+"doesn't exit");
        }
    }

    @Override
    public PlayerDto assignPlayerToTeam(Player player, long teamId) {
        Optional<Team> team=teamRepository.findById(teamId);
        if(team.isPresent())
        {
            player.setPlayerStatus(PlayerStatus.INSCRIT);
            player.setTeam(team.get());
            return playerMapper.convertPlayerToPlayerDTO(playerRepository.save(player));
        }else{
            throw new RuntimeException("team with id="+teamId+"doesn't exist");
        }
    }

    @Transactional
    @Override
    public TeamDto deletePlayerByIdFromTeam(long playerId,long teamId) throws PlayerNotFoundException {
        Optional<Team> team=teamRepository.findById(teamId);
        if(team.isPresent()){
            Team team1=team.get();
            List<Player> players=playerRepository.findAllByTeam_Id(teamId);
            Optional<Long> id=players.stream().filter(player -> player.getId()==playerId).findFirst().map(Player::getId);
                if(id.isPresent()){
                    playerRepository.deletePLayerInTeam(playerId);
                }else {
                    throw new PlayerNotFoundException("player with id"+playerId+
                            "doesn't exit");
                }
            return teamMapper.fromTeam(team1);
        }else {
            throw new RuntimeException("team with id doesn't exist");
        }
    }

    @Override
    public PlayerDto updatePlayerById(PlayerDto player, long id) {
        Optional<Player> optionalPlayer=playerRepository.findById(id);
        if (optionalPlayer.isPresent()){
            Player player1=optionalPlayer.get();
            if(player.getFirstName()!=null){
                player1.setFirstName(player.getFirstName());
            }
            if (player.getLastName()!=null){
                player1.setLastName(player.getLastName());
            }
            if (player.getPhoneNumber()!=null){
                player1.setPhoneNumber(player.getPhoneNumber());
            }
            if(player.getPlayerStatus()!=null){
                player1.setPlayerStatus(player.getPlayerStatus());
            }
            if(player.getEmail()!=null){
                player1.setEmail(player.getEmail());
            }
            if (player.getTeamName()!=null){
                Team team=new Team();
                team.setName(player.getTeamName());
                player1.setTeam(team);
            }
            return playerMapper.convertPlayerToPlayerDTO(playerRepository.save(player1));
        }
        else {
            throw new RuntimeException("player with id="+id+"doesn't exist");
        }
    }
    @Override
    public List<PlayerDto> getPlayersInTournoiByCardType(CardType cardType, long tournoiId) {
        List<Player> players=playerRepository.getAllInTournoiWithCard(cardType.ordinal(), tournoiId);
        return players.stream().map(player->playerMapper.convertPlayerToPlayerDTO(player))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerDto> getPlayersInMatchByCardType(long matchId, CardType cardType) {
        List<Player> players=playerRepository.getAllInMatchWithCard(cardType.ordinal(), matchId);
        return players.stream().map(player->playerMapper.convertPlayerToPlayerDTO(player))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerDto> getAllPlayersOfTournament(long tournament_id) {
        List<Player> players=playerRepository.getAllPlayersOfATournament(tournament_id);
        return players.stream().map(player->playerMapper.convertPlayerToPlayerDTO(player))
                .collect(Collectors.toList());
    }

    @Override
    public List<Match> notifyPlayers(long tournament_id, StatusTournamentAndMatch statusTournamentAndMatch) throws TeamNotFoundException, TournamentNotFoundException {

        // notify players by email and planify the matches
        Tournament tournament= tournamentRepository.findById(tournament_id).orElseThrow(()-> new TournamentNotFoundException(
                "Tournament with id " + tournament_id +" does not exist"
        ));
        List<Match> matches =getAllMatchesOfTournamentInThatPhase(tournament_id, statusTournamentAndMatch);
        List<Team> teams=new ArrayList<>();
        for (Match match:matches){
            teams.add(match.getWinnerTeam());
        }
        List<List<Team>> lists;
        lists= tirageService.lancer(teams);
        LocalDate date;

        if(statusTournamentAndMatch==StatusTournamentAndMatch.QUART_FINAL){
            date=tournamentRepository.findById(tournament_id).get().getStartDate().plusDays(3);
        }else {
            Match latestMatch = matches.get(0);
//            LocalDate latestDate = latestMatch.getStartTime();
//
//            for (Match match : matches) {
//                LocalDate currentDate = match.getStartTime();
//                if (currentDate.isAfter(latestDate)) {
//                    latestDate = currentDate;
//                    latestMatch = match;
//                }
//            }
            date=latestMatch.getStartTime().plusDays(3);
        }
        List<Match> result=new ArrayList<>();
        for (List<Team> list:lists){
                MatchRequestDTO matchRequestDTO=new MatchRequestDTO();
                matchRequestDTO.setStartTime(date);
                matchRequestDTO.setTeamId1(list.get(0).getId());
                matchRequestDTO.setTeamId2(list.get(1).getId());
                matchRequestDTO.setTournament(tournamentRepository.findById(tournament_id).get());
                matchRequestDTO.setStatusTournamentAndMatch(statusTournamentAndMatch);
                result.add(matchService.createMatch(matchRequestDTO));
        }

        Context context = new Context();
        context.setVariable("matches", matches);
        context.setVariable("teams",lists);
        String subject = "list of matches in the next round and the result of previous round";
        List<PlayerDto> players=new ArrayList<>();
        for(Match match:matches){
            for (Player player:match.getTeam1().getPlayers()){
                players.add(playerMapper.convertPlayerToPlayerDTO(player));
            }
            for (Player player:match.getTeam2().getPlayers()){
                players.add(playerMapper.convertPlayerToPlayerDTO(player));
            }
        }
        for (PlayerDto playerDto : players) {
            emailService.sendEmailWithHtmlTemplate(playerDto.getEmail(), subject, "email-template", context);
        }
        tournament.setStatusTournament(statusTournamentAndMatch);
        tournamentRepository.save(tournament);
        return result;
    }

    @Override
    public List<Match> getAllMatchesOfTournamentInThatPhase(Long tournamentId, StatusTournamentAndMatch statusTournamentAndMatch) {

        List<Match> matches=new ArrayList<>();
        StatusTournamentAndMatch previousStatus=getPreviousStatus(statusTournamentAndMatch);
        if ( previousStatus!= null) {
            matches=matchRepository.findMatchesByTournamentAndMatchStatus(tournamentId, previousStatus.toString());
        } else {
            System.out.println("in that phase "+statusTournamentAndMatch+"there are no previous matches");
        }
        return matches;
    }
    public StatusTournamentAndMatch getPreviousStatus(StatusTournamentAndMatch statusTournamentAndMatch){
        StatusTournamentAndMatch currentStatus = statusTournamentAndMatch;

        int currentStatusOrdinal = currentStatus.ordinal();

        StatusTournamentAndMatch previousStatus = null;
        if (currentStatusOrdinal > 1) {
            previousStatus = StatusTournamentAndMatch.values()[currentStatusOrdinal - 1];
        }
        return previousStatus;
    }
}
