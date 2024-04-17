package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlayersCardsDto {
    private Player player;
    private Long numberOfRedCards;
    private Long numberOfYellowCards;
}
