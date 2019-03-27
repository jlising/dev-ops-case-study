package com.ibm.ph.edm.common.services;

import com.ibm.extremeblue.w3java.Person;
import com.ibm.extremeblue.w3java.W3JavaException;
import com.ibm.extremeblue.w3java.W3JavaPersonNotFoundException;
import com.ibm.ph.edm.common.dto.EmployeeInfo;

import java.net.MalformedURLException;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface W3Service {
    Boolean isUserValid(String username, String password) throws W3JavaException;
    String getValueFromPersonByEmail(String email, String name) throws W3JavaPersonNotFoundException, W3JavaException;

    EmployeeInfo getOrCreateEmployeeFromW3(String email,boolean tl, boolean dm, boolean pem) throws W3JavaException;

    EmployeeInfo getEmployeeFromW3(String email) throws W3JavaException;

}
