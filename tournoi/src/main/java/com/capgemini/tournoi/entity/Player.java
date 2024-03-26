package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.PlayerStatus;
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
    @OneToMany
    private List<Card> cards;
    @ManyToOne
    private Team team;
}
