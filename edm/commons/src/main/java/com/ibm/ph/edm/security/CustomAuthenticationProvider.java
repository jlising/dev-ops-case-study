package com.ibm.ph.edm.security;

import com.ibm.extremeblue.w3java.Person;
import com.ibm.extremeblue.w3java.PersonQuery;
import com.ibm.extremeblue.w3java.W3JavaException;
import com.ibm.extremeblue.w3java.W3JavaPersonNotFoundException;
import com.ibm.ph.edm.common.dto.EmployeeInfo;
import com.ibm.ph.edm.common.dto.RoleInfo;
import com.ibm.ph.edm.common.services.EmployeeService;
import com.ibm.ph.edm.common.services.W3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.*;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    EmployeeService employeeService;

    @Autowired
    W3Service w3Service;

    /**
     * Called when user logs in
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();

        Boolean isValid = false;
        try {
            isValid = w3Service.isUserValid(name, password);
        } catch (W3JavaException e) {
            e.printStackTrace();
        }

        if (isValid) {
            final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();

            // Grant default role
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_STAFF"));

            // Query the database to get the additional roles
            EmployeeInfo employee = employeeService.findByEmail(name);

            if(employee != null){
                Set<RoleInfo> employeeRoles = employee.getAccessRoles();

                for(RoleInfo eRole : employeeRoles){
                    grantedAuths.add(new SimpleGrantedAuthority(eRole.getName()));
                }
            }

            final UserDetails principal = new User(name, password, grantedAuths);
            final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);

            return auth;
        } else {
            return null;
        }
    }

    /**
     * Support
     * @param authentication
     * @return
     */
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}