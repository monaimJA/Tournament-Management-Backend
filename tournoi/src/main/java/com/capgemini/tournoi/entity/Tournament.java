package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.StatusTournament;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String label;
    private Date startDate;
    private Date endDate;
    private StatusTournament statusTournament;

    @OneToMany(mappedBy = "tournament",cascade = CascadeType.ALL)
    private List<Team> teams;
}
