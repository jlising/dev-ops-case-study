package com.ibm.ph.edm.common.services;

import com.ibm.ph.edm.common.dto.EmployeeActionItemInfo;
import com.ibm.ph.edm.common.dto.EmployeeActionItemSelectionInfo;
import com.ibm.ph.edm.common.dto.EmployeeBasicInfo;
import com.ibm.ph.edm.common.entities.ActionItem;
import com.ibm.ph.edm.common.entities.EmployeeActionItem;
import com.ibm.ph.edm.paging.PageInfo;
import freemarker.template.TemplateException;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface EmployeeActionItemService {
    EmployeeActionItemInfo assign(EmployeeActionItem employeeActionItem);
    List<EmployeeActionItemInfo> assign(String actionItemId, List<EmployeeActionItemSelectionInfo> employeeActionItemSelections);

    PageInfo<EmployeeActionItemInfo> findAssignedEmployeesByActionItemId(String actionItemId, String search, Pageable pageable);
    PageInfo<EmployeeActionItemInfo> findActionItemsByEmployeeId(String employeeId, String search, Pageable pageable);
    List<EmployeeActionItem> findEmployeePendingActionItems(String employeeId);

    EmployeeActionItemInfo findByEmployeeIdAndActionItemId(String employeeId, String actionItemId);
    List<EmployeeActionItem> findByActionItemId(String actionItemId);

    void sendEmployeePendingActionItems() throws MessagingException, IOException, TemplateException;
}
