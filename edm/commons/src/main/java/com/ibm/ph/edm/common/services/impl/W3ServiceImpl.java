package com.ibm.ph.edm.common.services.impl;

import com.ibm.extremeblue.w3java.Person;
import com.ibm.extremeblue.w3java.PersonQuery;
import com.ibm.extremeblue.w3java.W3JavaException;
import com.ibm.extremeblue.w3java.W3JavaPersonNotFoundException;
import com.ibm.ph.edm.common.dto.EmployeeInfo;
import com.ibm.ph.edm.common.dto.EmployeeProjectInfo;
import com.ibm.ph.edm.common.entities.*;
import com.ibm.ph.edm.common.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Service
public class W3ServiceImpl implements W3Service {

    private static Logger LOG = LoggerFactory.getLogger(W3ServiceImpl.class);

    private Properties jndiProperties;
    private static final String SERVER_URL_SECURE = "ldap://bluepages.ibm.com:636";
    private static final String SERVER_URL_NONSECURE = "ldap://bluepages.ibm.com:389";

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeContactService employeeContactService;

    @Autowired
    EmployeeProjectService employeeProjectService;

    @Autowired
    EmployeeReimbursementService employeeReimbursementService;

    /**
     * Constructor
     */
    public W3ServiceImpl() {
        this.jndiProperties = null;
        this.jndiProperties = new Properties();
        this.jndiProperties.setProperty("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");

        this.jndiProperties.setProperty("java.naming.provider.url", SERVER_URL_NONSECURE);
        //this.jndiProperties.setProperty("java.naming.security.protocol", "ssl");
    }

    /**
     * Validate user via W3
     * @param username
     * @param password
     * @return
     */
    public Boolean isUserValid(String username, String password) throws W3JavaException {
        boolean result = false;

        Properties bindSpecific = this.cloneProperties(this.jndiProperties);
        DirContext context = null;

        PersonQuery query = new PersonQuery();
        query.searchForByInternetEmail(username);

        Person person = query.getFirst();

        if(person == null) {
            throw new W3JavaPersonNotFoundException("email address " + username + " was not found.");
        }else{
            String dn = person.getDN();
            bindSpecific.setProperty("java.naming.security.authentication", "simple");
            bindSpecific.setProperty("java.naming.security.principal", dn);
            bindSpecific.setProperty("java.naming.security.credentials", password);

            try {
                String message;
                try {
                    context = new InitialDirContext(bindSpecific);
                    result = true;
                } catch (javax.naming.AuthenticationException var24) {
                    result = false;
                } catch (AuthenticationNotSupportedException var25) {
                    throw new W3JavaException("JNDI authentication type simple not supported", var25);
                } catch (CommunicationException var26) {
                    message = "JNDI/LDAP Communication exception in AuthenticationTest" + var26.getMessage();
                    if(var26.getRootCause() instanceof ClassNotFoundException && var26.getRootCause().getMessage().indexOf("SSLSocketFactory") > -1) {
                        message = message + "; This usually happens if jsse.jar or ibmjsse.jar is not included in the library path. See the AuthenticationTest javadoc for more information.";
                    } else if(var26.getRootCause() instanceof NoClassDefFoundError && var26.getRootCause().getMessage().indexOf("javax/security/cert/CertificateException") > -1) {
                        message = message + "; This usually happens if you are using the Sun JSSE and jcert.jar is not included in the library path. See the AuthenticationTest javadoc for more information.";
                    } else if(var26.getRootCause() != null) {
                        message = message + "; root cause: " + var26.getRootCause().toString();
                    }

                    throw new W3JavaException(message, var26);
                } catch (NamingException var27) {
                    throw new W3JavaException("JNDI/LDAP Naming exception in AuthenticationTest", var27);
                } catch (NoClassDefFoundError var28) {
                    message = "NoClassDefFoundError in AuthenticationTest";
                    if(var28.getMessage().indexOf("javax/net/SocketFactory") > -1) {
                        message = message + "; This usually happens if you are using Sun JSSE and jnet.jar is not included in the library path. See the AuthenticationTest javadoc for more information.";
                    }

                    throw new W3JavaException(message, var28);
                }
            } finally {
                bindSpecific.remove("java.naming.security.principal");
                bindSpecific.remove("java.naming.security.credentials");

                try {
                    if(context != null) {
                        context.close();
                    }
                } catch (NamingException var23) {
                    ;
                }

                context = null;
            }

            return result;
        }
    }

    /**
     * Get field value from W3
     * @param email
     * @return
     */
    public String getValueFromPersonByEmail(String email, String name) throws W3JavaPersonNotFoundException, W3JavaException {
            PersonQuery query = null;
            query = new PersonQuery();
            query.searchForByInternetEmail(email);
            Person person = query.getFirst();

            if(person == null) {
                throw new W3JavaPersonNotFoundException(email);
            } else {
                String value = person.getValue(name);
                return value;
            }
    }

    /**
     * Clone JNDI properties
     * @param original
     * @return
     */
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

    /**
     * Insert records in the database from W3
     * @param email
     * @return
     */
    public EmployeeInfo getOrCreateEmployeeFromW3(String email, boolean tl, boolean dm, boolean pem) throws W3JavaException {

        EmployeeInfo employeeInfo = employeeService.findByEmail(email);

        if(employeeInfo == null){
            // Create new record based from W3
            Employee employee = new Employee();
            EmployeeContact employeeContact = new EmployeeContact();
            EmployeeProject employeeProject = new EmployeeProject();
            EmployeeReimbursement employeeReimbursement = new EmployeeReimbursement();

            employee.setEmail(email);

            employee.setFname(this.getValueFromPersonByEmail(email, "givenname"));
            employee.setLname(this.getValueFromPersonByEmail(email, "sn"));
            employee.setTl(tl);
            //employee.setSerial(this.getValueFromPersonByEmail(email, "ibmserialnumber"));
            employee.setSerial(this.getValueFromPersonByEmail(email, "uid"));
            employee.setIbmUID(this.getValueFromPersonByEmail(email, "uid"));
            employee.setPosition(this.getValueFromPersonByEmail(email, "jobresponsibilities"));
            employee.setPrimarySkillSet(this.getValueFromPersonByEmail(email, "jobresponsibilities"));
            employeeContact.setHomePhoneNumber(this.getValueFromPersonByEmail(email, "telephonenumber"));
            employeeContact.setMobileNumber(this.getValueFromPersonByEmail(email, "telephonenumber"));
            employeeContact.setMobileNumber(this.getValueFromPersonByEmail(email, "telephonenumber"));

            employee.setPem(pem);
            employee.setDm(dm);
            employee.setDateAdded(new Date());
            EmployeeInfo savedEmployeeInfo = employeeService.add(employee);

            employee.setId(savedEmployeeInfo.getId());
            employeeContact.setEmployee(employee);
            employeeContact.setEmailAddress(email);
            employeeContact.setPriority(1);
            employeeContactService.add(employeeContact);

            employeeProject.setEmployee(employee);
            employeeProject.setPriority(1);

            employeeProject.setBcpRole(BCPRole.NON_BCP_CRITICAL);
            employeeProject.setCriticalServiceSupported(false);
            EmployeeProjectInfo employeeProjectInfo = employeeProjectService.add(employeeProject);
            employeeProject.setId(employeeProjectInfo.getId());

            employeeReimbursement.setEmployee(employee);
            employeeReimbursement.setEmployeeProject(employeeProject);
            employeeReimbursement.setBroadBand(false);
            employeeReimbursement.setMobileData(false);
            employeeReimbursement.setMobileDevice(false);

            employeeReimbursementService.add(employeeReimbursement);

            employeeInfo = employeeService.findByEmail(email);
        }

        return employeeInfo;
    }

    /**
     * Get employee from w3
     * @param email
     * @return
     * @throws W3JavaException
     */
    public EmployeeInfo getEmployeeFromW3(String email) throws W3JavaException {
        // Create new record based from W3
        EmployeeInfo employeeInfo = new EmployeeInfo();

        employeeInfo.setEmail(email);

        employeeInfo.setFname(this.getValueFromPersonByEmail(email, "givenname"));
        employeeInfo.setLname(this.getValueFromPersonByEmail(email, "sn"));
        employeeInfo.setTl(false);

        employeeInfo.setSerial(this.getValueFromPersonByEmail(email, "uid"));
        employeeInfo.setIbmUID(this.getValueFromPersonByEmail(email, "uid"));
        employeeInfo.setPosition(this.getValueFromPersonByEmail(email, "jobresponsibilities"));
        employeeInfo.setPrimarySkillSet(this.getValueFromPersonByEmail(email, "jobresponsibilities"));

        return employeeInfo;
    }
}
