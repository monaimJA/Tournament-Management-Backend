package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Site;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTeam;

import lombok.Data;

import java.util.List;


@Data
public class TeamDto {
    
    private Long id;
    private String name;
    private Site site;
    private StatusTeam statusTeam;
    private List<PlayerDto> players;
    private TournamentResponseDto tournament;
}
