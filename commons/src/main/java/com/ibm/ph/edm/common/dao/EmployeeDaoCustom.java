package com.ibm.ph.edm.common.dao;

import com.ibm.extremeblue.w3java.W3JavaException;
import com.ibm.extremeblue.w3java.W3JavaPersonNotFoundException;
import com.ibm.ph.edm.common.dto.EmployeeInfo;
import com.ibm.ph.edm.common.entities.Employee;
import com.ibm.ph.edm.paging.PageInfo;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface EmployeeDaoCustom {
    Employee findByW3Email(String email) throws W3JavaPersonNotFoundException, W3JavaException;
}
