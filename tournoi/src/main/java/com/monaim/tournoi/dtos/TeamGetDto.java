package com.monaim.tournoi.dtos;

import com.monaim.tournoi.entity.Site;
import com.monaim.tournoi.enums.StatusTeam;
import lombok.Data;

import java.util.List;


@Data
public class TeamGetDto {

    private Long id;
    private String name;
    private Site site;
    private StatusTeam statusTeam;
    private List<PlayerTeamDto> players;
    //private TournamentTeamDto tournament;




}
