package com.andre.plataformavideos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    private String name;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    private String password;

    @ManyToMany
    @JoinTable(name="tb_user_role",
            joinColumns = @JoinColumn (name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role>  roles = new HashSet<>();

    public User(){

    }


    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole (Role role){
        roles.add(role);
    }

    public Boolean hasRole (String roleName){
        for (Role role : roles) {
            if (role.getAuthoriry().equals(roleName)) {
                return true;
            }
    }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
