package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.*;
import com.monaim.tournoi.entity.Match;
import com.monaim.tournoi.entity.Player;
import com.monaim.tournoi.entity.Team;
import com.monaim.tournoi.entity.Tournament;
import com.monaim.tournoi.enums.CardType;
import com.monaim.tournoi.enums.PlayerStatus;
import com.monaim.tournoi.enums.StatusTournamentAndMatch;
import com.monaim.tournoi.error.PlayerNotFoundException;
import com.monaim.tournoi.globalExceptions.TeamNotFoundException;
import com.monaim.tournoi.globalExceptions.TournamentNotFoundException;
import com.monaim.tournoi.mappers.PlayerMapper;
import com.monaim.tournoi.mappers.TeamMapper;
import com.monaim.tournoi.repos.MatchRepository;
import com.monaim.tournoi.repos.PlayerRepository;
import com.monaim.tournoi.repos.TeamRepository;
import com.monaim.tournoi.repos.TournamentRepository;
import com.monaim.tournoi.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public TeamDto deletePlayerByIdFromTeam(long playerId, long teamId) throws PlayerNotFoundException {
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
        if(matches!=null){
            for (Match match:matches){
                teams.add(match.getWinnerTeam());
            }
        }else {
            teams=teamRepository.findByTournamentId(tournament_id);
        }
        List<List<Team>> lists;
        lists= tirageService.lancer(teams);
        LocalDateTime date;

        if(statusTournamentAndMatch==StatusTournamentAndMatch.EIGHT_FINAL){
            date= tournamentRepository.findById(tournament_id).get().getStartDate().plusDays(3).atStartOfDay();
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
                matchRequestDTO.setStartTime(date.plusHours(Math.random() > 0.5? 17 : 20));
                matchRequestDTO.setTeamId1(list.get(0).getId());
                matchRequestDTO.setTeamId2(list.get(1).getId());
                matchRequestDTO.setTournament(tournamentRepository.findById(tournament_id).get());
                matchRequestDTO.setStatusTournamentAndMatch(statusTournamentAndMatch);
                result.add(matchService.createMatch(matchRequestDTO));
        }

        Context context = new Context();
        context.setVariable("matches", matches);
        context.setVariable("upcomingMatches", result);
        context.setVariable("teams",lists);
        context.setVariable("previousStatus", " ("+getPreviousStatus(tournament.getStatusTournament()).toString().replace("_", " de ").toLowerCase()+" )");
        context.setVariable("status"," ("+tournament.getStatusTournament().toString().replace("_", " de ").toLowerCase()+" )");
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
        List<Match> matches;
        StatusTournamentAndMatch previousStatus=getPreviousStatus(statusTournamentAndMatch);
        if ( previousStatus!= null && previousStatus!=StatusTournamentAndMatch.INSCRIPTION) {
            matches=matchRepository.findMatchesByTournamentAndMatchStatus(tournamentId, previousStatus.toString());
        } else {
            matches=null;
        }
        return matches;
    }
    public StatusTournamentAndMatch getPreviousStatus(StatusTournamentAndMatch statusTournamentAndMatch){
        StatusTournamentAndMatch currentStatus = statusTournamentAndMatch;

        int currentStatusOrdinal = currentStatus.ordinal();

        StatusTournamentAndMatch previousStatus = null;
        previousStatus = StatusTournamentAndMatch.values()[currentStatusOrdinal - 1];
        return previousStatus;
    }

    @Override
    public List<ScorersResponseDto> getTopScorers() {
//        Tournament tournament=tournamentRepository.findByInProgressTrue();
        return playerRepository.getTopScorers(1L);
    }

    @Override
    public List<PlayersCardsDto> getPlayersWithCardsNumber() {
//        Tournament tournament=tournamentRepository.findByInProgressTrue();
        return playerRepository.getPlayersCardsInfo(1L);
    }
}
