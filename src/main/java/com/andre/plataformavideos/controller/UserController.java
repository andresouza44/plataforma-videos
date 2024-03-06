package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.dto.UserDTO;
import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/me")
    public ResponseEntity<UserDTO> getMe (){
        UserDTO dto = service.getMe();

        return ResponseEntity.ok().body(dto);

    }



}
