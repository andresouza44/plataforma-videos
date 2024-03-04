package com.andre.plataformavideos.dto;

import com.andre.plataformavideos.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    Long id;
    String name;
    String email;

    List<String> roles = new ArrayList<>();

    public UserDTO(){

    }

    public UserDTO( User entity){
        this.name = entity.getName();
        this.email = entity.getEmail();

        entity.getRoles().forEach(role -> roles.add(role.getAuthority()));


    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
