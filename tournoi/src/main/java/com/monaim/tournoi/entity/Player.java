package com.monaim.tournoi.entity;

import com.monaim.tournoi.enums.PlayerStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "team")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @Enumerated(value = EnumType.STRING)
    private PlayerStatus playerStatus;
    @OneToMany
    private List<Card> cards;
    @ManyToOne
    @JoinColumn(name = "team_id",referencedColumnName = "id")
    @JsonIgnore
    private Team team;
}