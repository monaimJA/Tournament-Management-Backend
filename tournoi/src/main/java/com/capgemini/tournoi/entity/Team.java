package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.StatusTeam;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToOne
    private Site site;
    @Enumerated(value = EnumType.STRING)
    private StatusTeam statusTeam;
    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @ManyToOne
    private Tournament tournament ;


}
