package com.monaim.tournoi.repos;

import com.monaim.tournoi.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByTournamentId(Long tournamentId);

 @Query(value = "SELECT * FROM match m JOIN tournament t ON m.tournament_id = t.id WHERE t.id = :tournamentId AND m.status_match = :status", nativeQuery = true)
    public List<Match> findMatchesByTournamentAndMatchStatus(@Param("tournamentId") Long tournamentId, @Param("status") String statusTournamentAndMatch);

    @Query(value = "SELECT * FROM match m JOIN tournament t ON m.tournament_id = t.id WHERE m.status_match=t.status_tournament and t.in_progress=true", nativeQuery = true)
    public List<Match> getAllLatestMatchesInCurrentTournament();

    @Query(value = "SELECT * FROM match m JOIN tournament t ON m.tournament_id = t.id WHERE t.in_progress=true", nativeQuery = true)
    public List<Match> getAllMatchesInCurrentTournament();
}
