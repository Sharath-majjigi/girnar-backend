package com.backend.girnartour.services;


import com.backend.girnartour.models.User;
import com.backend.girnartour.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userDAO.findByEmail(username);
        if(user!=null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthorities(user));

        }else {
            throw new UsernameNotFoundException("User is not valid");
        }
    }

    private Set getAuthorities(User user){
        Set authorities=new HashSet();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        return authorities;
    }
}
