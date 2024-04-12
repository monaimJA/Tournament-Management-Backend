package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.enums.CardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class CardDto {
    @JsonIgnore
    private long id;
    @NotNull(message = "Date must be provided")
    private int minute;
    @NotNull(message = "CardType must be provided")
    private String cardType;
    @NotNull(message = "Player must be provided")
    private long player_id;
    @NotNull(message = "Match must be provided")
    private long match_id;
}
