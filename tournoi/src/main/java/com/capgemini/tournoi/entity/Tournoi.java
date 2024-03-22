package com.capgemini.tournoi.entity;

import com.capgemini.tournoi.enums.StatusTournoi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tournoi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String label;
    private Date startDate;
    private Date endDate;
    @Enumerated(value = EnumType.STRING)
    private StatusTournoi statusTournoi;

    @OneToMany
    private List<Team> teams;
}
