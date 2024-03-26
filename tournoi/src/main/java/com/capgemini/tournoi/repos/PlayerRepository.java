package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
    @Modifying
    @Query(value = "delete from player where id=?1",nativeQuery = true)
    public void deletePLayerInTeam(long id);
}
