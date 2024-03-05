package com.andre.plataformavideos.service;

import com.andre.plataformavideos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

 @Service
 public class AuthorizationService implements UserDetailsService {
        @Autowired
        UserRepository repositiry;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return repositiry.findByLogin(username);
        }

    }
