package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.CardDto;
import com.monaim.tournoi.entity.Card;
import com.monaim.tournoi.entity.Match;
import com.monaim.tournoi.mappers.CardMapper;
import com.monaim.tournoi.repos.CardRepository;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import com.monaim.tournoi.repos.MatchRepository;
import com.monaim.tournoi.repos.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    @Autowired
    public CardService(CardRepository cardRepository, CardMapper cardMapper, PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.playerRepository=playerRepository;
        this.matchRepository=matchRepository;
    }
    @Transactional
    public CardDto createCard(CardDto cardDto) throws Exception {
        try{
            Card card = cardMapper.convertToEntity(cardDto);

            Card cardResp = cardRepository.save(card);

            return cardMapper.convertToDto(cardResp);
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new Exception("Error occurred in the createCard : "+ cardDto.getId());

        }

    }

    public CardDto getCardById(Long id) throws Exception{
        try {
            Optional<Card> card = cardRepository.findById(id);
            if(card.isPresent()){
            return cardMapper.convertToDto(card.get());
            }
            else {
                throw new EntityNotFoundException("Card with id : "+id+" Not Found");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error occurred in the getCardById : "+ id);
        }

    }

    public List<CardDto> getAllCards() throws Exception{
        try {
            List<Card> cards = cardRepository.findAll();
            return cards.stream()
                    .map(card -> cardMapper.convertToDto(card))
                    .collect(Collectors.toList());
        }
        catch (Exception ex){
            throw new Exception("Error occurred in the getAllCards");
        }
    }

    public List<CardDto> getCardByMatchId(long match_id) throws Exception{
        try{
            List<Card> cards = cardRepository.findByMatchId(match_id);
            return cards.stream()
                    .map(card -> cardMapper.convertToDto(card))
                    .collect(Collectors.toList());
        }
        catch (Exception ex){
            throw new Exception("Error occurred in the getCardByMatchId : "+ match_id);
        }
    }

    public List<CardDto> getCardByPlayerId(long player_id) throws Exception{
        try{
            List<Card> cards = cardRepository.findByPlayerId(player_id);
            return cards.stream()
                    .map(card -> cardMapper.convertToDto(card))
                    .collect(Collectors.toList());
        }
        catch (Exception ex){
            throw new Exception("Error occurred in the getCardByPlayerId : "+ player_id);
        }
    }

    public boolean deleteCardById(long id) throws Exception{
        try{
            Optional<Card> cardOptional = cardRepository.findById(id);
            if (cardOptional.isPresent()) {
                cardRepository.deleteById(id);
                return true;
            }
            return false;
        }
        catch (Exception ex){
            throw new Exception("Error occurred in the deleteCardById : " + id);
        }
    }

    public CardDto updateCardById(CardDto cardDto) throws Exception{
        try{
            Card card = cardMapper.convertToEntity(cardDto);
            Card cardSaved = cardRepository.save(card);
            return cardMapper.convertToDto(card);
        }
        catch (Exception ex){
            throw new Exception("Error occurred in the updateCardById : "+ cardDto.getId());
        }
    }

    public List<CardDto> getCardByTournamentId(long tournamentId) throws Exception {
        try {
                List<Match> matches = matchRepository.findByTournamentId(tournamentId);
                List<Card> cards = cardRepository.findByMatchIn(matches);
            return cards.stream()
                    .map(card -> cardMapper.convertToDto(card))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new Exception("Error occurred in the getCardByTournamentId : " + tournamentId);
        }
    }
}
