package com.epam.esm.api.rest;

import com.epam.esm.api.security.AuthenticationService;
import com.epam.esm.service.data.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticateController {
    private final AuthenticationService authenticationService;

    public AuthenticateController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/token")
    public ResponseEntity generate(@RequestBody AuthenticationRequest authenticationRequest) {
        String token = authenticationService.createToken(authenticationRequest);
        Map<Object, Object> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
