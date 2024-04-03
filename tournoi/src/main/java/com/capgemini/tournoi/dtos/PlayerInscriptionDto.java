package com.capgemini.tournoi.dtos;


import com.capgemini.tournoi.enums.PlayerStatus;
import lombok.Data;

@Data
public class PlayerInscriptionDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private PlayerStatus playerStatus;
    private TeamDto team;
}
