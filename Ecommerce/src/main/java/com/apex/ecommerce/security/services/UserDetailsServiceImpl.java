package com.apex.ecommerce.security.services;

import com.apex.ecommerce.model.User;
import com.apex.ecommerce.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException(" User Not Found with username : "+ username));

        return UserDetailsImpl.build(user);
    }
}
