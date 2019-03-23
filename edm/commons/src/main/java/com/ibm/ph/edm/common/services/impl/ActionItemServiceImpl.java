package com.ibm.ph.edm.common.services.impl;

import com.ibm.ph.edm.common.dao.ActionItemDao;
import com.ibm.ph.edm.common.dao.EmployeeActionItemDao;
import com.ibm.ph.edm.common.dto.ActionItemInfo;
import com.ibm.ph.edm.common.dto.EmployeeActionItemInfo;
import com.ibm.ph.edm.common.dto.EmployeeInfo;
import com.ibm.ph.edm.common.entities.ActionItem;
import com.ibm.ph.edm.common.entities.EmployeeActionItem;
import com.ibm.ph.edm.common.services.ActionItemService;
import com.ibm.ph.edm.common.services.EmployeeActionItemService;
import com.ibm.ph.edm.common.services.Mail;
import com.ibm.ph.edm.paging.DataMapper;
import com.ibm.ph.edm.paging.MappableService;
import com.ibm.ph.edm.paging.PageInfo;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.ibm.ph.edm.common.entities.QActionItem.actionItem;
/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Service
public class ActionItemServiceImpl extends MappableService<ActionItemInfo, ActionItem> implements ActionItemService{
    private static Logger LOG = LoggerFactory.getLogger(ActionItemServiceImpl.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    ActionItemDao actionItemDao;

    @Autowired
    EmployeeActionItemDao employeeActionItemDao;

    @Autowired
    EmployeeActionItemService employeeActionItemService;

    @Autowired
    DataMapper dataMapper;

    @Override
    public PageInfo<ActionItemInfo> list(String search, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);
        JPAQuery countQuery = jpaQuery.clone(entityManager);

        // For counting records
        countQuery = countQuery.from(actionItem);

        Predicate predicate = actionItem.name.toLowerCase().like("%" + search + "%")
                .or(actionItem.description.toLowerCase().like("%" + search + "%"));

        if (search != null) {
            countQuery.where(predicate);
        }

        Long count = countQuery.count();

        // For fetching records
        jpaQuery = jpaQuery.from(actionItem)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (search != null) {
            jpaQuery.where(predicate);
        }

        List<ActionItem> actionItemList = jpaQuery.createQuery(actionItem).getResultList();

        return toPageInfo(new PageImpl<ActionItem>(actionItemList, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));
    }

    /**
     * Get action item by id
     * @param actionItemId
     * @return
     */
    public ActionItemInfo findById(String actionItemId) {
        ActionItem actionItem = actionItemDao.findById(actionItemId);

        return toDto(actionItem);
    }

    /**
     * Add new action item
     * @param actionItem
     * @return
     */
    public ActionItemInfo add(ActionItem actionItem) {
        actionItem.setDateAdded(new Date());
        ActionItem savedActionItem = actionItemDao.save(actionItem);

        return toDto(savedActionItem);
    }

    /**
     * Delete action item
     * @param actionItemId
     */
    public ActionItemInfo delete(String actionItemId) {

        ActionItem actionItem = actionItemDao.findById(actionItemId);

        //Delete employee action items
        //List<EmployeeActionItem> employeeActionItems = employeeActionItemService.findByActionItemId(actionItemId);
        //if(employeeActionItems.size() > 0){
        //    throw new Exception("Unable to delete action item. There are employees assigned.");
        //}

        /*for(EmployeeActionItem employeeActionItem : employeeActionItems){
            employeeActionItemDao.delete(employeeActionItem.getId());
        }*/

        actionItemDao.delete(actionItemId);
        return toDto(actionItem);
    }

    public ActionItemInfo patch(ActionItem tobePatchedActionItem) {
        ActionItem actionItem = actionItemDao.findById(tobePatchedActionItem.getId());

        if (actionItem != null) {
            // Convert to DTO
            ActionItemInfo actionItemInfo = toDto(actionItem);

            // Map tobePatchedEmployee to employeeInfo, this will leave null values in destination untouched. See DataMappingConfig mapNull(fals)
            dataMapper.map(tobePatchedActionItem, actionItemInfo);

            // Convert to entity so we can save to database
            ActionItem updatedActionItem = toEntity(actionItemInfo);

            ActionItem savedActionItem = actionItemDao.save(updatedActionItem);

            //Update due dates of employee assignments
            List<EmployeeActionItem> employeeActionItems = employeeActionItemService.findByActionItemId(tobePatchedActionItem.getId());

            for(EmployeeActionItem employeeActionItem : employeeActionItems){
                employeeActionItem.setDateDue(tobePatchedActionItem.getDateDue());
                employeeActionItemDao.save(employeeActionItem);
            }

            return toDto(savedActionItem);
        }

        return null;
    }
}
