package com.ibm.ph.edm.common.services;

import com.ibm.ph.edm.common.dto.EmployeeInfo;
import com.ibm.ph.edm.common.entities.Employee;
import com.ibm.ph.edm.paging.PageInfo;
import org.springframework.data.domain.Pageable;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface EmployeeService {
    PageInfo<EmployeeInfo> list(String search, Pageable pageable);
    EmployeeInfo findById(String employeeId);
    EmployeeInfo findByEmail(String email);

    EmployeeInfo add(Employee employee);
    EmployeeInfo patch(Employee tobePatchedEmployee);
    EmployeeInfo delete(String employeeId);

    EmployeeInfo tagAsTeamLead(Employee tobePatchedEmployee);
    EmployeeInfo tagAsPeopleManager(Employee tobePatchedEmployee);
    EmployeeInfo tagAsDeliveryManager(Employee tobePatchedEmployee);

    PageInfo<EmployeeInfo> findPeopleManagers(String search, Pageable pageable);
    PageInfo<EmployeeInfo> findDeliveryManagers(String search, Pageable pageable);
    PageInfo<EmployeeInfo> findTeamLeads(String search, Pageable pageable);
    PageInfo<EmployeeInfo> findEmployeesByDeliveryManager(String employeeId, String search, Pageable pageable);
}
