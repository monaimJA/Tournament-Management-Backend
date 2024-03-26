package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.PlayerStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private PlayerStatus playerStatus;
    @OneToMany(mappedBy = "player",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Card> cards;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id",referencedColumnName = "id")
    @JsonIgnore
    private Team team;
    @OneToMany(mappedBy = "player",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Goal> goals;
}
