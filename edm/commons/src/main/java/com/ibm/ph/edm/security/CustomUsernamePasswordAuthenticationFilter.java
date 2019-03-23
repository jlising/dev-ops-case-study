package com.ibm.ph.edm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static Logger LOG = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);

    private static final String CONTENT_TYPE = "application/json";
    private String jsonUsername;
    private String jsonPassword;

    @Override
    protected String obtainPassword(HttpServletRequest request) {
          return CONTENT_TYPE.equals(request.getHeader("Content-Type")) ? this.jsonPassword : super.obtainPassword(request);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request){
        return CONTENT_TYPE.equals(request.getHeader("Content-Type")) ? this.jsonUsername : super.obtainUsername(request);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

        if (CONTENT_TYPE.equals(request.getHeader("Content-Type"))) {
            try {
                /*
                 * HttpServletRequest can be read only once
                 */
                StringBuffer sb = new StringBuffer();
                String line = null;

                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }

                // JSON transformation
                ObjectMapper mapper = new ObjectMapper();
                LoginRequest loginRequest = mapper.readValue(sb.toString(), LoginRequest.class);

                this.jsonUsername = loginRequest.getUsername();
                this.jsonPassword = loginRequest.getPassword();

                this.jsonUsername = this.jsonUsername.trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.attemptAuthentication(request, response);
    }
}
