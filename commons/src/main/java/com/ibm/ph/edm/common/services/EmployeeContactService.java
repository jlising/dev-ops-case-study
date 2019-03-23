package com.ibm.ph.edm.common.services;

import com.ibm.ph.edm.common.dto.EmployeeContactInfo;
import com.ibm.ph.edm.common.entities.EmployeeContact;
import com.ibm.ph.edm.paging.PageInfo;
import org.springframework.data.domain.Pageable;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface EmployeeContactService {
    PageInfo<EmployeeContactInfo> list(String employeeId, Pageable pageable);
    EmployeeContactInfo findById(String employeeContactId);
    EmployeeContactInfo findEmployeeContactByPriority(String employeeId, int priority);

    EmployeeContactInfo add(EmployeeContact employeeContact);
    EmployeeContactInfo patch(EmployeeContact tobePatchedEmployeeContact);
    void delete(String employeeContactId);
}
