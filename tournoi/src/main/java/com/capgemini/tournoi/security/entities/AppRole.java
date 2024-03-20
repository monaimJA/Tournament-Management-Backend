package com.capgemini.tournoi.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data @ToString
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public AppRole(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
    public AppRole() {
    }
}
