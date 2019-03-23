package com.ibm.ph.edm.common.services.impl;

import com.ibm.ph.edm.common.dao.EmployeeProjectDao;
import com.ibm.ph.edm.common.dto.EmployeeProjectInfo;
import com.ibm.ph.edm.common.entities.EmployeeProject;
import com.ibm.ph.edm.paging.DataMapper;
import com.ibm.ph.edm.paging.MappableService;
import com.ibm.ph.edm.paging.PageInfo;
import com.ibm.ph.edm.common.services.EmployeeProjectService;
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

import static com.ibm.ph.edm.common.entities.QEmployeeProject.employeeProject;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Service
public class EmployeeProjectServiceImpl extends MappableService<EmployeeProjectInfo, EmployeeProject> implements EmployeeProjectService {
    private static Logger LOG = LoggerFactory.getLogger(EmployeeProjectServiceImpl.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    EmployeeProjectDao employeeProjectDao;

    @Autowired
    DataMapper dataMapper;

    /**
     * List all employee projects
     * @param employeeId
     * @param pageable
     * @return
     */
    public PageInfo<EmployeeProjectInfo> list(String employeeId, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        JPAQuery countQuery = jpaQuery.clone(entityManager);
        Long count = countQuery.from(employeeProject).where(employeeProject.employee.id.eq(employeeId)).count();

        List<EmployeeProject> projects = jpaQuery.from(employeeProject).where(employeeProject.employee.id.eq(employeeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .createQuery(employeeProject).getResultList();

        return  toPageInfo(new PageImpl<EmployeeProject>(projects, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));
    }

    /**
     * Get project by id
     * @param employeeProjectId
     * @return
     */
    public EmployeeProjectInfo findById(String employeeProjectId) {
        EmployeeProject employeeProject = employeeProjectDao.findById(employeeProjectId);

        return toDto(employeeProject);
    }

    /**
     * Add new project
     * @param employeeProject
     * @return
     */
    public EmployeeProjectInfo add(EmployeeProject employeeProject) {

        EmployeeProject savedEmployeeProject = employeeProjectDao.save(employeeProject);

        return toDto(savedEmployeeProject);
    }

    /**
     * Patch project
     * @param tobePatchedEmployeeProject
     * @return
     */
    public EmployeeProjectInfo patch(EmployeeProject tobePatchedEmployeeProject) {
        EmployeeProject savedEmployeeProject = employeeProjectDao.save(tobePatchedEmployeeProject);

        return toDto(savedEmployeeProject);
    }

    /**
     * Delete by id
     * @param employeeProjectId
     */
    public void delete(String employeeProjectId) {
        employeeProjectDao.delete(employeeProjectId);
    }

    /**
     * Find project by employee id and priority
     * @param employeeId
     * @param priority
     * @return
     */
    public EmployeeProjectInfo findEmployeeProjectByPriority(String employeeId, int priority) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        EmployeeProject project = (EmployeeProject) jpaQuery.from(employeeProject)
                                 .where(employeeProject.employee.id.eq(employeeId).and(employeeProject.priority.eq(priority)))
                                 .createQuery(employeeProject).getSingleResult();

        return  toDto(project);
    }
}
