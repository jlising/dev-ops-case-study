package com.ibm.ph.edm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Component
public class RESTLogoutSuccessHandler implements LogoutSuccessHandler{
    private static final String CONTENT_TYPE = "application/json";

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(CONTENT_TYPE);

        response.getWriter().print("{\"sucess\" : true }");
        response.getWriter().flush();
    }
}
