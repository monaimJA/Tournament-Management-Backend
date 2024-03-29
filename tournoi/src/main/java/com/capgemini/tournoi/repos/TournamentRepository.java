package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Long> {

}
