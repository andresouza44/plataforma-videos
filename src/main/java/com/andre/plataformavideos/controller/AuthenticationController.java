package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.dto.AuthenticationDTO;
import com.andre.plataformavideos.dto.LoginResponseDTO;
import com.andre.plataformavideos.dto.RegisterDTO;
import com.andre.plataformavideos.entity.User;
import com.andre.plataformavideos.infra.security.TokenService;
import com.andre.plataformavideos.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(),dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    @PostMapping(value="/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDTO dto){
        if (repository.findByLogin(dto.login()) != null) return ResponseEntity.badRequest().build();
        var encriptPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = new User (dto.login(),encriptPassword, dto.role());
        repository.save(newUser);
        return ResponseEntity.ok().build();
    }


}
