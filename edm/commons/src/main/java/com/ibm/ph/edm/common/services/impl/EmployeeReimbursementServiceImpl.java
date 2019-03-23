package com.ibm.ph.edm.common.services.impl;

import com.ibm.ph.edm.common.dao.EmployeeReimbursementDao;
import com.ibm.ph.edm.common.dto.EmployeeReimbursementInfo;
import com.ibm.ph.edm.common.entities.EmployeeReimbursement;
import com.ibm.ph.edm.paging.MappableService;
import com.ibm.ph.edm.paging.PageInfo;
import com.ibm.ph.edm.common.services.EmployeeReimbursementService;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ibm.ph.edm.common.entities.QEmployeeReimbursement.employeeReimbursement;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Service
public class EmployeeReimbursementServiceImpl extends MappableService<EmployeeReimbursementInfo, EmployeeReimbursement> implements EmployeeReimbursementService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    EmployeeReimbursementDao employeeReimbursementDao;

    /**
     * List all employee reimbursement
     * @param employeeId
     * @param pageable
     * @return
     */
    public PageInfo<EmployeeReimbursementInfo> list(String employeeId, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        JPAQuery countQuery = jpaQuery.clone(entityManager);
        Long count = countQuery.from(employeeReimbursement).where(employeeReimbursement.employee.id.eq(employeeId)).count();

        List<EmployeeReimbursement> contacts = jpaQuery.from(employeeReimbursement).where(employeeReimbursement.employee.id.eq(employeeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .createQuery(employeeReimbursement).getResultList();

        return  toPageInfo(new PageImpl<EmployeeReimbursement>(contacts, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));

    }

    /**
     * Get employee reimbursement by id
     * @param employeeReimbursementId
     * @return
     */
    public EmployeeReimbursementInfo findById(String employeeReimbursementId) {
        EmployeeReimbursement employeeReimbursement = employeeReimbursementDao.findById(employeeReimbursementId);
        return toDto(employeeReimbursement);
    }

    /**
     * Add new employee reimbursement
     * @param employeeReimbursement
     * @return
     */
    public EmployeeReimbursementInfo add(EmployeeReimbursement employeeReimbursement) {
        EmployeeReimbursement savedEmployeeReimbursement = employeeReimbursementDao.save(employeeReimbursement);

        return toDto(savedEmployeeReimbursement);
    }

    /**
     * Patch employee reimbursement
     * @param tobePatchedEmployeeReimbursement
     * @return
     */
    public EmployeeReimbursementInfo patch(EmployeeReimbursement tobePatchedEmployeeReimbursement) {
        EmployeeReimbursement savedEmployeeReimbursement = employeeReimbursementDao.save(tobePatchedEmployeeReimbursement);

        return toDto(savedEmployeeReimbursement);
    }

    /**
     * Delete employee reimbursement
     * @param employeeReimbursementId
     */
    public void delete(String employeeReimbursementId) {
        employeeReimbursementDao.delete(employeeReimbursementId);
    }

    /**
     * Find reimbursements by employee project
     * @param employeeId
     * @param priority
     * @return
     */
    public EmployeeReimbursementInfo findReimbursementsByEmployeeProjectAndPriority(String employeeId, int priority) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        EmployeeReimbursement reimbursement = (EmployeeReimbursement) jpaQuery.from(employeeReimbursement)
                .leftJoin(employeeReimbursement.employeeProject)
                .where(employeeReimbursement.employee.id.eq(employeeId), employeeReimbursement.employeeProject.priority.eq(priority))
                .createQuery(employeeReimbursement).getSingleResult();

        return  toDto(reimbursement);
    }
}
