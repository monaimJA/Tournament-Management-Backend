package com.monaim.tournoi.repos;

import com.monaim.tournoi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByTournamentId(Long tournamentId);

    @Query(value = "select * from team t inner join ",nativeQuery = true)
    List<Team> getAllTeamsNotEliminated(Long tournamentId);

}
