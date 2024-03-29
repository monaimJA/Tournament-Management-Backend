package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {
}
