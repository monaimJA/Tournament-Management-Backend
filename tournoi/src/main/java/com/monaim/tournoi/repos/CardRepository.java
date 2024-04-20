package com.monaim.tournoi.repos;

import com.monaim.tournoi.entity.Card;
import com.monaim.tournoi.entity.Match;
import com.monaim.tournoi.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByMatchId(long matchId);
    List<Card> findByPlayerId(long playerId);
    int countAllByPlayer_IdAndCardType(Long id, CardType cardType);
    List<Card> findByMatchIn(List<Match> matches);
}
