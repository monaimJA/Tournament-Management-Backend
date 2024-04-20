package com.monaim.tournoi.dtos;


import com.monaim.tournoi.enums.PlayerStatus;
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
