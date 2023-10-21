package com.maakapyaar.usermanagement.service;

import com.maakapyaar.usermanagement.model.document.CustomUserDetails;
import com.maakapyaar.usermanagement.model.document.User;
import com.maakapyaar.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User fetchedUser = userRepository.findByEmail(username);
        if(null == fetchedUser){
            throw new RuntimeException("User with email id not found: "+ username);
        }
        return new CustomUserDetails(fetchedUser);
    }
}