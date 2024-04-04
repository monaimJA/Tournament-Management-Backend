package com.capgemini.tournoi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class MatchRequestDTO {
    private long id;
    private Date startTime;
    private Long teamId1;
    private Long teamId2;

}
