package com.epam.esm.api.security;


import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthorizationComponent {
    boolean principalHasAccess(@NonNull UserDetails principal,@NonNull Long id);
    boolean isUserOrder(@NonNull UserDetails principal,@NonNull Long id);
}
