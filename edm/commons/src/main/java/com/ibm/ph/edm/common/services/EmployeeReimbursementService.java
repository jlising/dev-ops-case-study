package com.ibm.ph.edm.common.services;

import com.ibm.ph.edm.common.dto.EmployeeReimbursementInfo;
import com.ibm.ph.edm.common.entities.EmployeeReimbursement;
import com.ibm.ph.edm.paging.PageInfo;
import org.springframework.data.domain.Pageable;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface EmployeeReimbursementService {

    PageInfo<EmployeeReimbursementInfo> list(String employeeId, Pageable pageable);
    EmployeeReimbursementInfo findById(String employeeReimbursementId);
    EmployeeReimbursementInfo findReimbursementsByEmployeeProjectAndPriority(String employeeId, int priority);

    EmployeeReimbursementInfo add(EmployeeReimbursement employeeReimbursement);
    EmployeeReimbursementInfo patch(EmployeeReimbursement tobePatchedEmployeeReimbursement);
    void delete(String employeeReimbursementId);
}
