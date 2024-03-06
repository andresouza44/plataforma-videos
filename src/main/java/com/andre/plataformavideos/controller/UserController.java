package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.dto.UserDTO;
import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/me")
    public ResponseEntity<UserDTO> getMe () {
        UserDTO dto = service.getMe();

        return ResponseEntity.ok().body(dto);

    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> addNewUser (@RequestBody UserDTO dto){
        UserDTO userDTO = service.addNewUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }


}
