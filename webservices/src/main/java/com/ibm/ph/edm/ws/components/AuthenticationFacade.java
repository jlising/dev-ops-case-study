package com.ibm.ph.edm.ws.components;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}