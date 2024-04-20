package com.monaim.tournoi.security.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class UserForm{
    private String username;
    private String password;
    private String confirmedPassword;
}
