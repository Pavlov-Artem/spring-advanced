package com.epam.esm.api.security;

import com.epam.esm.service.data.AuthenticationRequest;

public interface AuthenticationService {
    String createToken(AuthenticationRequest authenticationRequest);
}
