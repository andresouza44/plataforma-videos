package com.andre.plataformavideos.service;

import com.andre.plataformavideos.dto.RoleDTO;
import com.andre.plataformavideos.dto.UserDTO;
import com.andre.plataformavideos.entity.Role;
import com.andre.plataformavideos.entity.User;
import com.andre.plataformavideos.projections.UserDetailsProjection;
import com.andre.plataformavideos.repositories.RoleRepository;
import com.andre.plataformavideos.repositories.UserRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepositiry repository;

    @Autowired
    private RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.size() == 0){
            throw  new UsernameNotFoundException("User no found");
        }

        User user = new User();
        user.setEmail(result.get(0).getUserName());
        user.setPassword(result.get(0).getPassword());
        result.forEach(projection -> user.addRole(new Role(projection.getRoleId(), projection.getAuthority())));

        return user;
    }

    protected User authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return repository.findByEmail(username).get();

        } catch (Exception e) {
            throw new UsernameNotFoundException("Email not found");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getMe(){
        User user = authenticated();
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO addNewUser(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        String encryptPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(encryptPassword);

        List<RoleDTO> roles = roleService.findAll();

        roles.forEach(role -> dto.getRoles()
                .forEach(dtoRole -> {
                    if (role.getAuthority().equals(dtoRole)) {
                        user.addRole(new Role(role.getId(), role.getAuthority()));
                    }
                        }
                ));

        if (user.getRoles().isEmpty()){
            user.addRole(new Role(2L,"ROLE_USER"));
        }
        repository.save(user);

        return new UserDTO(user);
    }
}
