package com.ibm.ph.edm.common.dao;

import com.ibm.extremeblue.w3java.W3JavaException;
import com.ibm.extremeblue.w3java.W3JavaPersonNotFoundException;
import com.ibm.ph.edm.common.entities.Employee;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface EmployeeDao extends BaseJpaDao<Employee, String>, EmployeeDaoCustom {
    Employee findByEmail(String email);
}
