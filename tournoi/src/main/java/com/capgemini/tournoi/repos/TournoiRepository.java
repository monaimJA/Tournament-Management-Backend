package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournoiRepository extends JpaRepository<Tournament, Long> {
}
