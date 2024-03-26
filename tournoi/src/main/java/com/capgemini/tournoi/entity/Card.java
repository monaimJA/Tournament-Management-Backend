package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date date;

    private CardType cardType;

    @ManyToOne
    @JoinColumn(name = "player_id",referencedColumnName = "id")
    private Player player;

}
