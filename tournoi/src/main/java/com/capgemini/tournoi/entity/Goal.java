package com.capgemini.tournoi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int value;
    private LocalTime time;
    @ManyToOne
    Score score;

    @OneToOne
    private Player player;
}
