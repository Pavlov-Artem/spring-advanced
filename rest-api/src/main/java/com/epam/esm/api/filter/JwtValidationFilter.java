package com.epam.esm.api.filter;

import com.epam.esm.api.security.JwtTokenProvider;
import io.jsonwebtoken.SignatureException;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Order(value = HIGHEST_PRECEDENCE + 1)
public class JwtValidationFilter extends OncePerRequestFilter {

    private static final String USERNAME_REQUEST_ATTRIBUTE = "email";
    private static final String JWT_REQUEST_ATTRIBUTE = "jwt";
    private static final int BEARER_HEADER_OFFSET = 7;
    private static final String BEARER = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    public JwtValidationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private void validateJwt(HttpServletRequest request) {

        final String requestTokenHeader = request.getHeader(AUTHORIZATION);

        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER)) {
            String jwtToken = requestTokenHeader.substring(BEARER_HEADER_OFFSET);
            try {
                if (!jwtTokenProvider.validateToken(jwtTokenProvider.resolveToken(request))) {
                    throw new IllegalArgumentException("JWT token is expired or invalid");
                }
                String username = jwtTokenProvider.getUsernameFromToken(jwtToken);
                request.setAttribute(USERNAME_REQUEST_ATTRIBUTE, username);
                request.setAttribute(JWT_REQUEST_ATTRIBUTE, jwtToken);
            } catch (SignatureException e) {
                throw new AccessDeniedException("Calculating JWT signature failed: JWT signature does not match locally computed signature.");
            } catch (IllegalArgumentException e) {
                throw new AccessDeniedException("Unable to get JWT Token");
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        validateJwt(request);
        filterChain.doFilter(request, response);
    }
}
