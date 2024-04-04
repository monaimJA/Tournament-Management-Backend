package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByTournamentId(Long tournamentId);

}
