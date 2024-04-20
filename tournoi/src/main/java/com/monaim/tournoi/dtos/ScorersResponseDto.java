package com.monaim.tournoi.dtos;

import com.monaim.tournoi.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ScorersResponseDto {
    private Player player;
    private Long score;
}
