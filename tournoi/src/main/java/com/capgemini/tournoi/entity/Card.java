package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.CardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int minute;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

}
