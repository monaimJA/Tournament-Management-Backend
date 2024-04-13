package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.StatusTournamentAndMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate startTime;
    private LocalDate overTime;
    @Enumerated(value = EnumType.STRING)
    private StatusTournamentAndMatch statusMatch;


    @OneToOne
    private Team team1;
    @OneToOne
    private  Team team2;

    @OneToMany
    private List<Card> cards;


    @OneToOne(cascade = CascadeType.ALL)
    private Score score;



    @OneToOne
    private Team winnerTeam;

    @OneToOne
    private  Tournament tournament;
}
