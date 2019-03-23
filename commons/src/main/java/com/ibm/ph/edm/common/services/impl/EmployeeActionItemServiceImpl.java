package com.ibm.ph.edm.common.services.impl;

import com.ibm.ph.edm.common.dao.ActionItemDao;
import com.ibm.ph.edm.common.dao.EmployeeActionItemDao;
import com.ibm.ph.edm.common.dao.EmployeeDao;
import com.ibm.ph.edm.common.dto.EmployeeActionItemInfo;
import com.ibm.ph.edm.common.dto.EmployeeActionItemSelectionInfo;
import com.ibm.ph.edm.common.entities.ActionItem;
import com.ibm.ph.edm.common.entities.Employee;
import com.ibm.ph.edm.common.entities.EmployeeActionItem;
import com.ibm.ph.edm.common.services.EmployeeActionItemService;
import com.ibm.ph.edm.common.services.Mail;
import com.ibm.ph.edm.paging.MappableService;
import com.ibm.ph.edm.paging.PageInfo;
import com.mysema.query.jpa.impl.JPAQuery;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.ibm.ph.edm.common.entities.QEmployeeActionItem.employeeActionItem;
import static com.ibm.ph.edm.common.entities.QEmployee.employee;
/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@PropertySource("classpath:mail.properties")
@Service
public class EmployeeActionItemServiceImpl  extends MappableService<EmployeeActionItemInfo, EmployeeActionItem> implements EmployeeActionItemService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ActionItemDao actionItemDao;

    @Autowired
    EmployeeActionItemDao employeeActionItemDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Value("${mail.from}")
    private String mailFrom;

    @Override
    public EmployeeActionItemInfo assign(EmployeeActionItem employeeActionItem) {
        EmployeeActionItem savedEmolyeeEmployeeActionItem = employeeActionItemDao.save(employeeActionItem);
        return toDto(savedEmolyeeEmployeeActionItem);
    }

    @Override
    public List<EmployeeActionItemInfo> assign(String actionItemId, List<EmployeeActionItemSelectionInfo> employeeActionItemSelections) {
        List<EmployeeActionItemInfo> assignedActionItems = new ArrayList<EmployeeActionItemInfo>();
        ActionItem actionItem = actionItemDao.findById(actionItemId);

        EmployeeActionItemInfo employeeActionItemInfo;
        EmployeeActionItem employeeActionItem;
        Employee employee;

        for(EmployeeActionItemSelectionInfo employeeActionItemSelectionInfo : employeeActionItemSelections){

            employee = employeeDao.findById(employeeActionItemSelectionInfo.getEmployee_id());
            employeeActionItemInfo = this.findByEmployeeIdAndActionItemId(employeeActionItemSelectionInfo.getEmployee_id(), actionItemId);

            if(employeeActionItemSelectionInfo.isSelected()){
                //Do not add if employee id and action item id exists
                if( employeeActionItemInfo == null){
                    employeeActionItem = new EmployeeActionItem();
                    employeeActionItem.setId(actionItemId);
                    employeeActionItem.setEmployee(employee);
                    employeeActionItem.setDateAdded(new Date());
                    employeeActionItem.setActionItem(actionItem);
                    employeeActionItem.setDateDue(actionItem.getDateDue());
                    assignedActionItems.add(this.assign(employeeActionItem));
                }
            }else{
                if(employeeActionItemInfo != null){
                    employeeActionItemDao.delete(employeeActionItemInfo.getId());
                }
            }
        }

        return assignedActionItems;
    }

    @Override
    public PageInfo<EmployeeActionItemInfo> findAssignedEmployeesByActionItemId(String actionItemId, String search, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        JPAQuery countQuery = jpaQuery.clone(entityManager);
        Long count = countQuery.from(employeeActionItem).where(employeeActionItem.actionItem.id.eq(actionItemId)).count();

        List<EmployeeActionItem> actionItems = jpaQuery.from(employeeActionItem).where(employeeActionItem.actionItem.id.eq(actionItemId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .createQuery(employeeActionItem).getResultList();

        return  toPageInfo(new PageImpl<EmployeeActionItem>(actionItems, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));
    }

    @Override
    public PageInfo<EmployeeActionItemInfo> findActionItemsByEmployeeId(String employeeId, String search, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        JPAQuery countQuery = jpaQuery.clone(entityManager);
        Long count = countQuery.from(employeeActionItem).where(employeeActionItem.employee.id.eq(employeeId)).count();

        List<EmployeeActionItem> actionItems = jpaQuery.from(employeeActionItem).where(employeeActionItem.employee.id.eq(employeeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .createQuery(employeeActionItem).getResultList();

        return  toPageInfo(new PageImpl<EmployeeActionItem>(actionItems, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));

    }

    /**
     * Find employee's pending action items
     * @return
     */
    @Override
    public List<EmployeeActionItem> findEmployeePendingActionItems(String employeeId){
        JPAQuery jpaQuery = new JPAQuery(entityManager);
        List<EmployeeActionItem> actionItems = jpaQuery.from(employeeActionItem)
                 .where(employeeActionItem.employee.id.eq(employeeId), employeeActionItem.dateCompleted.isNull())
                .createQuery(employeeActionItem).getResultList();

        return actionItems;
    }

    private List<Employee> _findEmployeesWithPendingActionItems(){
        JPAQuery jpaQuery = new JPAQuery(entityManager);
        List<Employee> employees = jpaQuery.distinct().from(employeeActionItem)
                .where(employeeActionItem.dateCompleted.isNull())
                .createQuery(employeeActionItem.employee).getResultList();

        return employees;
    }

    @Override
    public EmployeeActionItemInfo findByEmployeeIdAndActionItemId(String employeeId, String actionItemId) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        EmployeeActionItemInfo employeeActionItemInfo = null;
        try {
            EmployeeActionItem actionItem = (EmployeeActionItem) jpaQuery.from(employeeActionItem).where(employeeActionItem.employee.id.eq(employeeId), employeeActionItem.actionItem.id.eq(actionItemId))
                    .createQuery(employeeActionItem).getSingleResult();

            employeeActionItemInfo =  toDto(actionItem);
        }catch (NoResultException nre){}


        return employeeActionItemInfo;
    }

    @Override
    public List<EmployeeActionItem> findByActionItemId(String actionItemId) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);
        List<EmployeeActionItem> actionItems = null;

        try {
           actionItems = jpaQuery.from(employeeActionItem).where(employeeActionItem.actionItem.id.eq(actionItemId))
                   .createQuery(employeeActionItem).getResultList();

        }catch (NoResultException nre){}


        return actionItems;
    }

    @Override
    public void sendEmployeePendingActionItems() throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        List<Employee> employees = this._findEmployeesWithPendingActionItems();

        for(Employee employee : employees){
            String employeeId = employee.getId();
            String fname = employee.getFname();
            String emailAddress = employee.getEmployeeContacts().get(0).getEmailAddress();

            //if(emailAddress == "navarrw@ph.ibm.com"){ continue; }

            List<EmployeeActionItem> employeePendingActionItems = this.findEmployeePendingActionItems(employeeId);

            if(employeePendingActionItems.size() > 0){
                Mail mail = new Mail();
                mail.setFrom(this.mailFrom);
                mail.setTo(emailAddress);
                mail.setSubject("Call Tree Management Tool - Your pending action items");

                Map model = new HashMap();
                model.put("name", fname);
                model.put("logoUrl", "http://localhost:4200/assets/images/logo.png");
                model.put("appLink", "http://localhost:4200/");
                model.put("employeeActionItems", employeePendingActionItems);

                mail.setModel(model);

                Template template = freemarkerConfig.getTemplate("pending-action-items.ftl");
                String htmlString = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());

                helper.setTo(mail.getTo());
                helper.setText(htmlString, true);
                helper.setSubject(mail.getSubject());
                helper.setFrom(mail.getFrom());

                emailSender.send(message);
            }
        }
    }
}
