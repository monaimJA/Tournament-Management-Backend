package com.capgemini.tournoi.dtos;


import com.capgemini.tournoi.enums.PlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private long id;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phoneNumber;
    private PlayerStatus playerStatus;
    private String teamName;
    private Integer numberOfRedCards;
    private Integer numberOfYellowCards;
}
