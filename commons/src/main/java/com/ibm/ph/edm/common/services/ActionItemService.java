package com.ibm.ph.edm.common.services;

import com.ibm.ph.edm.common.dto.ActionItemInfo;
import com.ibm.ph.edm.common.dto.EmployeeActionItemInfo;
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

public interface ActionItemService {
    PageInfo<ActionItemInfo> list(String search, Pageable pageable);
    ActionItemInfo findById(String employeeId);
    ActionItemInfo add(ActionItem actionItem);
    ActionItemInfo delete(String actionItemId);
    ActionItemInfo patch(ActionItem tobePatchedActionItem);
}
