package com.epam.esm.api.security;

import com.epam.esm.data.User;
import com.epam.esm.service.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findUserByUsername(username);
        return new UserDetailsEntity(user);
    }

    private User findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByEmail(username);
        return userOptional.orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username '%s' not found", username))
        );
    }
}
