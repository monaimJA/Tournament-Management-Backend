package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    @Query(value = "select * from tournament t where t.in_progress=true",nativeQuery = true)
    public Tournament checkExistTournamentInProgress();

    Tournament findByInProgressTrue();
}
