package com.ibm.ph.edm.ws.components;

import org.springframework.security.core.Authentication;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
