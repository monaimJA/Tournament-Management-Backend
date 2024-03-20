package com.capgemini.tournoi.service;

import com.capgemini.tournoi.entities.AppRole;
import com.capgemini.tournoi.entities.AppUser;

public interface AccountService {
    public AppUser saveUser(String username,String password,String confirmedPassword);
    public AppRole save(AppRole role);
    public AppUser loadUserByUsername(String username);
    public void addRoleToUser(String username,String rolename);
}
