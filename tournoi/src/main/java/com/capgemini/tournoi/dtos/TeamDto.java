package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.enums.StatusTeam;
import com.capgemini.tournoi.enums.StatusTournament;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private long id;
    private String name;
    private String siteName;
    private StatusTeam statusTeam;
    private List<String> players;
    private Boolean exclusion;
    private String tournamentName;
    private StatusTournament statusTournoi;
}
