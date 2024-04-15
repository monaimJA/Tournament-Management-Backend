package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Card;
import com.capgemini.tournoi.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByMatchId(long matchId);
    List<Card> findByPlayerId(long playerId);
    int countAllByPlayer_IdAndCardType(Long id, CardType cardType);
    List<Card> findByTournamentId(long tournamentId);
}
