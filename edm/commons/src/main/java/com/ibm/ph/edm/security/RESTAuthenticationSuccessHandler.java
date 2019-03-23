package com.ibm.ph.edm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.extremeblue.w3java.W3JavaException;
import com.ibm.ph.edm.common.dto.EmployeeInfo;
import com.ibm.ph.edm.common.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Component
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static Logger LOG = LoggerFactory.getLogger(RESTAuthenticationSuccessHandler.class);

    private static final String CONTENT_TYPE = "application/json";

    @Autowired
    W3Service w3Service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        clearAuthenticationAttributes(request);

        ObjectMapper objectMapper = new ObjectMapper();

        // Get the employee records from the database, if not exists, create the record based from the info return from W3
        EmployeeInfo employeeInfo = null;

        try {
            employeeInfo = w3Service.getOrCreateEmployeeFromW3(authentication.getName(), false, false, false);
        } catch (W3JavaException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        String jsonInString = objectMapper.writeValueAsString(employeeInfo);
        response.setContentType(CONTENT_TYPE);
        response.getWriter().print(jsonInString);
        response.getWriter().flush();
    }
}
