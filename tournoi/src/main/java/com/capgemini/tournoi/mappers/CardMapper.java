package com.capgemini.tournoi.mappers;

import com.capgemini.tournoi.dtos.CardDto;
import com.capgemini.tournoi.entity.Card;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.enums.CardType;
import com.capgemini.tournoi.repos.MatchRepository;
import com.capgemini.tournoi.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;


@Service
public class CardMapper {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    @Autowired
    public CardMapper(PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    public CardDto convertToDto(Card card) {
        if (card == null) {
            return null;
        }

        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setMinute(card.getMinute());

        if (card.getCardType() != null) {
            cardDto.setCardType(card.getCardType().name());
        }

        if (card.getPlayer() != null) {
            cardDto.setPlayer_id(card.getPlayer().getId());
        }

        if (card.getMatch() != null) {
            cardDto.setMatch_id(card.getMatch().getId());
        }

        return cardDto;
    }

    public Card convertToEntity(CardDto cardDto) throws EntityNotFoundException {
        if (cardDto == null) {
            return null;
        }

        Card card = new Card();
        card.setId(cardDto.getId());
        card.setMinute(cardDto.getMinute());

        if (cardDto.getCardType() != null) {
            card.setCardType(CardType.valueOf(cardDto.getCardType()));
        }

        if (cardDto.getPlayer_id() > 0) {
            // Fetch Player from database or throw exception if not found
            Player player = playerRepository.findById(cardDto.getPlayer_id())
                    .orElseThrow(() -> new EntityNotFoundException("Player not found with ID: " + cardDto.getPlayer_id()));
            card.setPlayer(player);
        }

        if (cardDto.getMatch_id() > 0) {
            // Fetch Match from database or throw exception if not found
            Match match = matchRepository.findById(cardDto.getMatch_id())
                    .orElseThrow(() -> new EntityNotFoundException("Match not found with ID: " + cardDto.getMatch_id()));
            card.setMatch(match);
        }

        return card;
    }
}