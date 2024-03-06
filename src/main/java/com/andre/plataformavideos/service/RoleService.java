package com.andre.plataformavideos.service;


import com.andre.plataformavideos.dto.RoleDTO;
import com.andre.plataformavideos.entity.Role;
import com.andre.plataformavideos.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional(readOnly = true)
   public List<RoleDTO> findAll(){
        List<Role> roles = repository.findAll();
        return roles.stream().map(role -> new RoleDTO(role)).toList();
    }

}
