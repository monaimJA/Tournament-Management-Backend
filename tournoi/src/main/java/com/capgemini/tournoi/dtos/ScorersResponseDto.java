package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ScorersResponseDto {
    private Player player;
    private Long score;
}
