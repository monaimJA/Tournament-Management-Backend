package com.capgemini.tournoi.controllers;

import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.enums.CardType;
import com.capgemini.tournoi.enums.StatusTournament;
import com.capgemini.tournoi.error.PlayerNotFoundException;
import com.capgemini.tournoi.services.PlayerService;
import com.capgemini.tournoi.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/team/{id}/players")
    public ResponseEntity<List<PlayerDto>> getAllPlayersOfATeam(@PathVariable("id") long id){
        List<PlayerDto> players=playerService.getAllPlayersOfATeam(id);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable("id") long id) throws PlayerNotFoundException {
        PlayerDto playerDto=playerService.getPlayerById(id);
        return new ResponseEntity<>(playerDto,HttpStatus.OK);
    }
    @PostMapping("/team/{teamId}")
    public ResponseEntity<PlayerDto> assignPlayerToTeam(@RequestBody Player player, @PathVariable("teamId") long teamId){
        PlayerDto playerDto=playerService.assignPlayerToTeam(player,teamId);
        return new ResponseEntity<>(playerDto,HttpStatus.OK);
    }
    @DeleteMapping("/{playerId}/team/{teamId}")
    public ResponseEntity<TeamDto> deletePlayerByIdFromTeam(@PathVariable("playerId") long playerId,
                                                         @PathVariable("teamId") long teamId) throws PlayerNotFoundException {
        TeamDto teamDto=playerService.deletePlayerByIdFromTeam(playerId,teamId);
        return new ResponseEntity<>(teamDto,HttpStatus.OK);
    }
    @PutMapping("/{playerId}")
    public ResponseEntity<PlayerDto> updatePlayerById(@PathVariable("playerId") long playerId,
                                                   @RequestBody PlayerDto player){
        PlayerDto playerDto=playerService.updatePlayerById(player,playerId);
        return new ResponseEntity<>(playerDto,HttpStatus.OK);
    }
    @GetMapping("/tournoi/{tournoiId}")
    public ResponseEntity<List<PlayerDto>> getPlayersInTournoiByCardType(@RequestParam CardType cardType,
                                                                      @PathVariable("tournoiId") long tournoiId){
        List<PlayerDto> players=playerService.
                getPlayersInTournoiByCardType(cardType,tournoiId);
        return new ResponseEntity<>(players,HttpStatus.OK);
    }
    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<PlayerDto>> getPlayersInMatchByCardType(@RequestParam("cardType") CardType cardType,
                                                                      @PathVariable("matchId") long matchId){
        List<PlayerDto> players=playerService.
                getPlayersInMatchByCardType(matchId,cardType);
        return new ResponseEntity<>(players,HttpStatus.OK);
    }
    @GetMapping("/changeStatus/{tournament_id}")
    public ResponseEntity<String> notifyPlayers(@PathVariable long tournament_id,
                                                @RequestParam("statusTournament") StatusTournament statusTournament){
        playerService.notifyPlayers(tournament_id,statusTournament);
        return ResponseEntity.ok("HTML email sent successfully!");
    }
}
