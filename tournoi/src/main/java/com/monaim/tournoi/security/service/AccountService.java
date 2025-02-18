package com.monaim.tournoi.security.service;

import com.monaim.tournoi.security.entities.AppRole;
import com.monaim.tournoi.security.entities.AppUser;

public interface AccountService {
    public AppUser saveUser(String username, String password, String confirmedPassword);
    public AppRole save(AppRole role);
    public AppUser loadUserByUsername(String username);
    public void addRoleToUser(String username,String rolename);
}
