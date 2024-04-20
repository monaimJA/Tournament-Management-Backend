package com.monaim.tournoi.dtos;

import com.monaim.tournoi.entity.Player;
import com.monaim.tournoi.entity.Site;
import com.monaim.tournoi.entity.Tournament;
import com.monaim.tournoi.enums.StatusTeam;

import lombok.Data;

import java.util.List;


@Data
public class TeamDto {
    
    private Long id;
    private String name;
    private Site site;
    private StatusTeam statusTeam;
    private List<Player> players;
    private Tournament tournament;
}
