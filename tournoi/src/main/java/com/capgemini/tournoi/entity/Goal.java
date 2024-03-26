package com.capgemini.tournoi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int value;
    private int time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id",referencedColumnName = "id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "score_id",referencedColumnName = "id")
    private Score score;
}
