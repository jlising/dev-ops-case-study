package com.ibm.ph.edm.ws.controllers;

import com.ibm.ph.edm.ws.components.IAuthenticationFacade;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private static Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    /**
     * Authentication ping
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Boolean ping(){
        LOG.info("Method ping() in class {} was called to checked if logged in.", this.getClass().getName());

        return true;
    }

    /**
     * Get authentication details
     * @return
     */
    @RequestMapping(value = "/username", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public String currentUserNameSimple() {
        Authentication authentication = authenticationFacade.getAuthentication();

        return authentication.getName();
    }
}
