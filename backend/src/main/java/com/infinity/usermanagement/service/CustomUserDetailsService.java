package com.infinity.usermanagement.service;

import com.infinity.usermanagement.model.document.CustomUserDetails;
import com.infinity.usermanagement.model.document.User;
import com.infinity.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> fetchedUser = userRepository.findByEmail(username);
        if(null == fetchedUser){
            throw new RuntimeException("User with email id not found: "+ username);
        }
        return new CustomUserDetails(fetchedUser.get());
    }
}