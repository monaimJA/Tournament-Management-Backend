package com.monaim.tournoi.entity;

import com.monaim.tournoi.enums.StatusTeam;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
