package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.StatusTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    private Site site;
    private StatusTeam statusTeam;
    @OneToMany
    private List<Player> players;

    @ManyToOne
    private Tournament tournament;

    //@OneToOne
    //private Match match;
}
