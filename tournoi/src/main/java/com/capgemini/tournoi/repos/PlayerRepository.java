package com.capgemini.tournoi.repos;

import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
    @Modifying
    @Query(value = "delete from player where id=?1",nativeQuery = true)
    public void deletePLayerInTeam(long id);
    public List<Player> findAllByTeam_Id(long team_id);
    @Query(value = "select * from player p inner join team t on t.id=p.team_id inner join" +
            " tournament tr on tr.id=t.tournament_id inner join card c on " +
            "c.player_id=p.id where tr.id=?2 and c.card_type=?1",nativeQuery = true)
    public List<Player> getAllInTournoiWithCard(long card_type,long tournoi_id);
    @Query(value = "select * from player p inner join team t on t.id=p.team_id inner join" +
            " match m1 on t.id=m1.team1_id inner join card c on c.player_id=p.id where m1.id=?2" +
            " and c.card_type=?1 union select * from player p inner join team t on t.id=p.team_id" +
            " inner join match m2 on t.id=m2.team2_id inner join card c on c.player_id=p.id where" +
            " m2.id=?2 and c.card_type=?1",nativeQuery = true)
    public List<Player> getAllInMatchWithCard(long card_type,long match_id);

    @Query(value = "select * from player p inner join team t on t.id=p.team_id " +
            "inner join tournament tr on tr.id=t.tournament_id where tr.id=?1"
            ,nativeQuery = true)
    public List<Player> getAllPlayersOfATournament(long tournament_id);
}