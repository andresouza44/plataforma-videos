package com.andre.plataformavideos.dto;


import com.andre.plataformavideos.entity.Role;

import java.util.List;
import java.util.Objects;

public class RoleDTO {
    private Long id;
    private String authority;

    public RoleDTO (Role role){
        id = role.getId();
        authority = role.getAuthority();

    }


    public RoleDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleDTO roleDTO = (RoleDTO) o;

        return Objects.equals(authority, roleDTO.authority);
    }

    @Override
    public int hashCode() {
        return authority != null ? authority.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}
