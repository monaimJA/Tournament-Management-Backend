package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}
