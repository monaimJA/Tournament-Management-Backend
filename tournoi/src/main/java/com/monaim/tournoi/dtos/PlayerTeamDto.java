package com.monaim.tournoi.dtos;


import lombok.Data;

@Data

public class PlayerTeamDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
