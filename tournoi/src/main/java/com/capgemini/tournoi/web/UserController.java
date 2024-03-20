package com.capgemini.tournoi.web;

import lombok.Data;
import com.capgemini.tournoi.entities.AppUser;
import com.capgemini.tournoi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/register")
    public AppUser register(@RequestBody  UserForm userForm){
        return  accountService.saveUser(
                userForm.getUsername(),userForm.getPassword(),userForm.getConfirmedPassword());
    }
}
@Data
class UserForm{
    private String username;
    private String password;
    private String confirmedPassword;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public String getConfirmedPassword() {
        return password;
    }

}
