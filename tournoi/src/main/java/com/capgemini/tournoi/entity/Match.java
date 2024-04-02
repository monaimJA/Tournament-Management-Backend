package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.enums.StatusMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String startTime;
    private String overTime;
    private StatusMatch statusMatch;


    @OneToOne
    private Team team1;
    @OneToOne
    private  Team team2;

    @OneToMany
    private List<Card> cards;


     @OneToOne
    private Score score;

    @ManyToMany
    @JoinTable(name = "match_scorers",
            joinColumns = @JoinColumn(name = "match_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "player_id",
                    referencedColumnName = "id"))
    private List<Player> scorers;

    @OneToMany
    private List<Goal> goals;






}
