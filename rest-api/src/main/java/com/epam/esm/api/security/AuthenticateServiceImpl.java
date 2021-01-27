package com.epam.esm.api.security;

import com.epam.esm.service.data.AuthenticationRequest;
import com.epam.esm.service.exceptions.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateServiceImpl implements AuthenticationService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticateServiceImpl(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String createToken(AuthenticationRequest authReq) {
        String username = authReq.getEmail();
        String password = authReq.getPassword();
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new EntityNotFoundException("Incorrect username or password", 0L);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenProvider.generateToken(userDetails);
    }
}
