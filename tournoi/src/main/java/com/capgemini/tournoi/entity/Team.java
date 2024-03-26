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
    @OneToMany(mappedBy = "team",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Player> players;

    @ManyToOne
    @JoinColumn(name = "tournament_id",referencedColumnName = "id")
    private Tournament tournament;
}
