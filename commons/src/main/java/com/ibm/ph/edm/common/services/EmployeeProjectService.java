package com.ibm.ph.edm.common.services;

import com.ibm.ph.edm.common.dto.EmployeeProjectInfo;
import com.ibm.ph.edm.common.entities.EmployeeProject;
import com.ibm.ph.edm.paging.PageInfo;
import org.springframework.data.domain.Pageable;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface EmployeeProjectService {

    PageInfo<EmployeeProjectInfo> list(String employeeId, Pageable pageable);
    EmployeeProjectInfo findById(String employeeProjectId);
    EmployeeProjectInfo findEmployeeProjectByPriority(String employeeId, int priority);

    EmployeeProjectInfo add(EmployeeProject employeeProject);
    EmployeeProjectInfo patch(EmployeeProject tobePatchedEmployeeProject);
    void delete(String employeeProjectId);
}
