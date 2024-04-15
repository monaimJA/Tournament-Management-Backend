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
    @Query(value = "select * from match m inner join tournament tr on tr.id=m.tournament_id where tr.in_progress=?1",nativeQuery = true)
    List<Match> getMatchesInCurrentTournament(boolean inProgress);

   @Query(value = "SELECT * FROM match m JOIN tournament t ON m.tournament_id = t.id WHERE t.id = :tournamentId AND m.status_match = :status", nativeQuery = true)
    public List<Match> findMatchesByTournamentAndMatchStatus(@Param("tournamentId") Long tournamentId, @Param("status") String statusTournamentAndMatch);

    @Query(value = "SELECT * FROM match m JOIN tournament t ON m.tournament_id = t.id WHERE m.status_match=t.status_tournament and t.in_progress=true", nativeQuery = true)
    public List<Match> getAllMatchesInCurrentTournament();

    @Query(value = "SELECT * FROM match m JOIN tournament t ON m.tournament_id = t.id WHERE t.in_progress=true", nativeQuery = true)
    public List<Match> getAllLatestMatchesInCurrentTournament();
    @Query(value = "select * from match m inner join tournament tr on tr.id=m.tournament_id where tr.in_progress=?1 and tr.status_tournament=m.status_match",nativeQuery = true)
    List<Match> getLatestMatchesInCurrentTournament(boolean inProgress);
}
