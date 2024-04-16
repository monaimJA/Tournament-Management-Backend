package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query(value = "select * from match m INNER JOIN score s ON m.score_id=s.id and " +
            "m.id= :matchId", nativeQuery = true)
    List<Score> findByMatch(@Param(value = "matchId") Long matchId);
}
