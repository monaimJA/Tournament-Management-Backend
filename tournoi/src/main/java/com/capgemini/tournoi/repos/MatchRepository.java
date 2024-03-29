package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MatchRepository extends JpaRepository<Match, Long> {

}
