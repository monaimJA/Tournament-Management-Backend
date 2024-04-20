package com.monaim.tournoi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GoalDto {
    @JsonIgnore
    private Long id;
    @NotNull(message = "Player must be provided")
    private Long playerId;
    @NotNull(message = "Match must be provided")
    private Long matchId;
    @Min(value = 1, message = "Minute must be greater than 0")
    private int minute;

}
