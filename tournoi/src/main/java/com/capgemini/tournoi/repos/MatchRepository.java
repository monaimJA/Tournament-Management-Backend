package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTournamentAndMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Repository

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTournamentId(Long tournamentId);
    //public List<Match> findAllByTournament_IdAndStatusMatch(Long tournamentId, StatusTournamentAndMatch statusTournamentAndMatch);

   @Query(value = "SELECT * FROM match m JOIN tournament t ON m.tournament_id = t.id WHERE t.id = :tournamentId AND m.status_match = :status", nativeQuery = true)
    public List<Match> findMatchesByTournamentAndMatchStatus(@Param("tournamentId") Long tournamentId, @Param("status") String statusTournamentAndMatch);
}
