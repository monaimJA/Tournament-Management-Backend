package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Site;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.StatusTeam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {
    private Long id;
    private String name;
    private Site site;
    private StatusTeam statusTeam;
}
