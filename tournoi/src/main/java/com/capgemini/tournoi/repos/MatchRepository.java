package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.enums.StatusTournamentAndMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query(value = "select * from match m inner join tournament tr on tr.id=m.tournament_id where tr.in_progress=?1",nativeQuery = true)
    List<Match> getMatchesInCurrentTournament(boolean inProgress);

    @Query(value = "select * from match m inner join tournament tr on tr.id=m.tournament_id where tr.in_progress=?1 and tr.status_tournament=m.status_match",nativeQuery = true)
    List<Match> getLatestMatchesInCurrentTournament(boolean inProgress);
}
