package com.andre.plataformavideos.dto;

import com.andre.plataformavideos.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    Long id;
    String name;
    String email;
    String password;

    List<String> roles = new ArrayList<>();

    public UserDTO(){

    }

    public UserDTO( User entity){
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();

        entity.getRoles().forEach(role -> roles.add(role.getAuthority()));


    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
