package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.StatusTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    private Site site;
    private StatusTeam statusTeam;
    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @ManyToOne
    private Tournament tournament ;


}
