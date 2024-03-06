package com.andre.plataformavideos.dto;

import com.andre.plataformavideos.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    String name;

    @UniqueElements(message = "email já cadastrado")
    @Email (message = "Digite um email válido")
    @NotBlank(message = "O email não pode estar em branco")
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
