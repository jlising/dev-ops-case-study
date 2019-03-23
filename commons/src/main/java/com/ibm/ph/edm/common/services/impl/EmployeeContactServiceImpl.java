package com.ibm.ph.edm.common.services.impl;

import com.ibm.ph.edm.common.dao.EmployeeAddressDao;
import com.ibm.ph.edm.common.dao.EmployeeContactDao;
import com.ibm.ph.edm.common.dto.EmployeeContactInfo;
import com.ibm.ph.edm.common.entities.EmployeeAddress;
import com.ibm.ph.edm.common.entities.EmployeeContact;
import com.ibm.ph.edm.paging.MappableService;
import com.ibm.ph.edm.paging.PageInfo;
import com.ibm.ph.edm.common.services.EmployeeContactService;
import com.mysema.query.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ibm.ph.edm.common.entities.QEmployeeContact.employeeContact;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Service
public class EmployeeContactServiceImpl extends MappableService<EmployeeContactInfo, EmployeeContact> implements EmployeeContactService {
    private static Logger LOG = LoggerFactory.getLogger(EmployeeContactServiceImpl.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    EmployeeContactDao employeeContactDao;

    @Autowired
    EmployeeAddressDao employeeAddressDao;

    /**
     * List all employee contacts
     * @param employeeId
     * @param pageable
     * @return
     */
    public PageInfo<EmployeeContactInfo> list(String employeeId, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        JPAQuery countQuery = jpaQuery.clone(entityManager);
        Long count = countQuery.from(employeeContact).where(employeeContact.employee.id.eq(employeeId)).count();

        List<EmployeeContact> contacts = jpaQuery.from(employeeContact).where(employeeContact.employee.id.eq(employeeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .createQuery(employeeContact).getResultList();

        return  toPageInfo(new PageImpl<EmployeeContact>(contacts, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));
    }

    /**
     * Get contact by id
     * @param employeeContactId
     * @return
     */
    public EmployeeContactInfo findById(String employeeContactId) {
        EmployeeContact employeeContact = employeeContactDao.findById(employeeContactId);

        return toDto(employeeContact);
    }

    /**
     * Add new employee contact
     * @param employeeContact
     * @return
     */
    public EmployeeContactInfo add(EmployeeContact employeeContact) {
        EmployeeContact savedEmployeeContact =  employeeContactDao.save(employeeContact);

        return toDto(savedEmployeeContact);
    }

    /**
     * Patch contact
     * @param tobePatchedEmployeeContact
     */
    public EmployeeContactInfo patch(EmployeeContact tobePatchedEmployeeContact) {
         EmployeeContact savedEmployeeContact = employeeContactDao.save(tobePatchedEmployeeContact);

         // Remove  unused employees addresses
         // findByEmployeeId is a default query method (see Query Creation section at https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.core-concepts)
         List<EmployeeAddress> employeeAddress = employeeAddressDao.findByEmployeeId(tobePatchedEmployeeContact.getEmployee().getId());

         for(EmployeeAddress address : employeeAddress){
            if(savedEmployeeContact.getHomeEmployeeAddress().getId().equals(address.getId())
                    || savedEmployeeContact.getBusinessEmployeeAddress().getId().equals(address.getId())) continue;
            employeeAddressDao.delete(address.getId()) ;
         }

         return toDto(savedEmployeeContact);
    }

    /**
     * Delete contact
     * @param employeeContactId
     */
    public void delete(String employeeContactId) {
        employeeContactDao.delete(employeeContactId);
    }

    /**
     * Get employee contact by priority
     * @param employeeId
     * @param priority
     * @return
     */
    public EmployeeContactInfo findEmployeeContactByPriority(String employeeId, int priority) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        EmployeeContact contact = (EmployeeContact) jpaQuery.from(employeeContact)
                .where(employeeContact.employee.id.eq(employeeId).and(employeeContact.priority.eq(priority)))
                .createQuery(employeeContact).getSingleResult();

        return  toDto(contact);
    }
}
