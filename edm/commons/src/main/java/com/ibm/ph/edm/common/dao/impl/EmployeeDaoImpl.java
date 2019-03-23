package com.ibm.ph.edm.common.dao.impl;

import com.ibm.extremeblue.w3java.Person;
import com.ibm.extremeblue.w3java.PersonQuery;
import com.ibm.extremeblue.w3java.W3JavaException;
import com.ibm.extremeblue.w3java.W3JavaPersonNotFoundException;
import com.ibm.ph.edm.common.dao.EmployeeDaoCustom;
import com.ibm.ph.edm.common.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.persistence.EntityManager;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class EmployeeDaoImpl implements EmployeeDaoCustom {
    private Properties jndiProperties;
    private static final String SERVER_URL_SECURE = "ldap://bluepages.ibm.com:636";

    /**
     * Find by email and password
     * @param email
     * @return
     * @throws W3JavaPersonNotFoundException
     * @throws W3JavaException
     */
    public Employee findByW3Email(String email) throws W3JavaPersonNotFoundException, W3JavaException{
        boolean result = false;
        this.jndiProperties = null;
        this.jndiProperties = new Properties();
        this.jndiProperties.setProperty("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");

        this.jndiProperties.setProperty("java.naming.provider.url", SERVER_URL_SECURE);
        this.jndiProperties.setProperty("java.naming.security.protocol", "ssl");

        Properties bindSpecific = this.cloneProperties(this.jndiProperties);

        DirContext context = null;
        PersonQuery query = new PersonQuery();
        query.searchForByInternetEmail(email);
        Person person = query.getFirst();

        if(person == null) {
            return null;
        } else {
            return null;
        }
    }

    private Properties cloneProperties(Properties original) {
        Properties newInstance = new Properties();
        Enumeration enumeration = original.propertyNames();

        while(enumeration.hasMoreElements()) {
            String name = (String)enumeration.nextElement();
            String value = original.getProperty(name);
            newInstance.setProperty(name, value);
        }

        return newInstance;
    }


}
