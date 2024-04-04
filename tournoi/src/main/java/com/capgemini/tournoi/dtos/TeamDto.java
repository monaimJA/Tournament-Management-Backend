package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Site;
import com.capgemini.tournoi.enums.StatusTeam;
import com.capgemini.tournoi.enums.StatusTournament;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private long id;
    private String name;
    private StatusTeam statusTeam;
    private List<String> players;
    private Boolean exclusion;
    private String tournamentName;
    private StatusTournament statusTournoi;
    private Site site;
}
