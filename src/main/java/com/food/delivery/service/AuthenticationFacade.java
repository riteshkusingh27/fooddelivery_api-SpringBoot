package com.food.delivery.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    public Authentication getAuthentication();
}
