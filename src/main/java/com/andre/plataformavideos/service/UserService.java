package com.andre.plataformavideos.service;

import com.andre.plataformavideos.entity.Role;
import com.andre.plataformavideos.entity.User;
import com.andre.plataformavideos.projections.UserDetailsProjection;
import com.andre.plataformavideos.repositories.UserRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepositiry repositiry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repositiry.searchUserAndRolesByEmail(username);
        if (result.size() == 0){
            throw  new UsernameNotFoundException("User no found");
        }

        User user = new User();
        user.setEmail(result.get(0).getUserName());
        user.setPassword(result.get(0).getPassword());
        result.forEach(projection -> user.addRole(new Role(projection.getRoleId(), projection.getAuthority())));

        return user;
    }
}
