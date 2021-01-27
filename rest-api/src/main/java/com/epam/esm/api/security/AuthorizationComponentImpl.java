package com.epam.esm.api.security;

import com.epam.esm.api.validation.AuthorizationException;
import com.epam.esm.data.User;
import com.epam.esm.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthorizationComponentImpl implements AuthorizationComponent {

    private final UserService userService;

    public AuthorizationComponentImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean principalHasAccess(UserDetails principal, Long id) {
        if (id == null) {
            throw new AuthorizationException("The given id must not be null!");
        }
        User user = userService.getById(id);
        Collection<? extends GrantedAuthority> roles = principal.getAuthorities();
        if (roles.stream().anyMatch(r -> r.getAuthority().contains("ROLE_ADMIN"))) {
            return true;
        } else if (principal.getUsername().equals(user.getEmail())) {
            return true;
        } else {
            throw new AccessDeniedException("access denied");
        }
    }

    @Override
    public boolean isUserOrder(UserDetails principal, Long id) {
        User user = userService.getByEmail(principal.getUsername());
        Collection<? extends GrantedAuthority> roles = principal.getAuthorities();
        if (roles.stream().anyMatch(r -> r.getAuthority().contains("ROLE_ADMIN"))) {
            return true;
        } else if (user.getUserOrders().stream().anyMatch(o -> o.getId().equals(id))) {
            return true;
        } else {
            throw new AccessDeniedException("access denied");
        }
    }
}