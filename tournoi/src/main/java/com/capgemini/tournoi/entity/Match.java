package com.capgemini.tournoi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date startTime;
    private Date overTime;

    @ManyToMany
    @JoinTable(name = "match_teams",
            joinColumns = @JoinColumn(name = "match_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id",
                    referencedColumnName = "id"))
    private List<Team> teams;

    @OneToMany
    private List<Avertissement> avertissements;


    @OneToOne
    private Score score;
}
