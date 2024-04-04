package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Goal;
import com.capgemini.tournoi.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByPlayer(Player player);
}
