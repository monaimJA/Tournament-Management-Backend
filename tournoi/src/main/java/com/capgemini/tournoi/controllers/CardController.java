package com.capgemini.tournoi.controllers;

import com.capgemini.tournoi.dtos.CardDto;
import com.capgemini.tournoi.services.CardService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardDto> createCard(@Valid @RequestBody CardDto cardDto) throws Exception {
        CardDto cardDtoResp = cardService.createCard(cardDto);
        return new ResponseEntity<>(cardDtoResp, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable Long id) throws Exception {
            CardDto card = cardService.getCardById(id);
            return ResponseEntity.ok(card);
    }

    @GetMapping("/allcards")
    public ResponseEntity<List<CardDto>> getAllCards() throws Exception {
            List<CardDto> cardsDto = cardService.getAllCards();
            return ResponseEntity.ok(cardsDto);
    }

    @GetMapping("/by-match/{match_id}")
    public ResponseEntity<List<CardDto>> getCardByMatchId(@PathVariable long match_id) throws Exception {
        List<CardDto> cards = cardService.getCardByMatchId(match_id);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/by-player/{player_id}")
    public ResponseEntity<List<CardDto>> getCardByPlayerId(@PathVariable long player_id) throws Exception {
        List<CardDto> cards = cardService.getCardByPlayerId(player_id);
        return ResponseEntity.ok(cards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCardById(@PathVariable Long id) throws Exception {
        if (cardService.deleteCardById(id)){
            return ResponseEntity.ok("Card with ID " + id + " deleted successfully");
        }
        else{
            return ResponseEntity.badRequest()
                    .body("Failed to delete card with ID " + id + ". Please verify the data.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDto> updateCardById(@PathVariable Long id, @Valid @RequestBody CardDto cardDto) throws Exception {
        cardDto.setId(id);
        return ResponseEntity.ok(cardService.updateCardById(cardDto));
    }

    @GetMapping("/by-tournament/{tournament-id}")
    public ResponseEntity<List<CardDto>> getCardByTournamentId(@PathVariable long tournament_id) throws Exception {
        List<CardDto> cards = cardService.getCardByTournamentId(tournament_id);
        return ResponseEntity.ok(cards);
    }
}

