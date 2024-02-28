package com.andre.plataformavideos.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name =" tb_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authoriry;

    public Role(){

    }

    public Role(Long id, String authoriry) {
        this.id = id;
        this.authoriry = authoriry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthoriry() {
        return authoriry;
    }

    public void setAuthoriry(String authoriry) {
        this.authoriry = authoriry;
    }

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return Objects.equals(authoriry, role.authoriry);
    }

    @Override
    public int hashCode() {
        return authoriry != null ? authoriry.hashCode() : 0;
    }
}
