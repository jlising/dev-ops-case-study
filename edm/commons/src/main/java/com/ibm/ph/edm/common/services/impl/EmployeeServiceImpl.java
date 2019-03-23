package com.ibm.ph.edm.common.services.impl;

import com.ibm.ph.edm.common.dao.EmployeeContactDao;
import com.ibm.ph.edm.common.dao.EmployeeDao;
import com.ibm.ph.edm.common.dao.EmployeeProjectDao;
import com.ibm.ph.edm.common.dto.EmployeeInfo;
import com.ibm.ph.edm.common.entities.Employee;
import com.ibm.ph.edm.common.services.EmployeeService;
import com.ibm.ph.edm.paging.DataMapper;
import com.ibm.ph.edm.paging.MappableService;
import com.ibm.ph.edm.paging.PageInfo;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ibm.ph.edm.common.entities.QEmployee.employee;
import static com.ibm.ph.edm.common.entities.QEmployeeProject.employeeProject;
/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Service
public class EmployeeServiceImpl extends MappableService<EmployeeInfo, Employee> implements EmployeeService {

    private static Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    EmployeeProjectDao employeeProjectDao;

    @Autowired
    EmployeeContactDao employeeContactDao;

    @Autowired
    DataMapper  dataMapper;

    /**
     * Get list of employees
     * @param pageable
     * @return
     */
    public PageInfo<EmployeeInfo> list(String search, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);
        JPAQuery countQuery = jpaQuery.clone(entityManager);

        // For counting records
        countQuery = countQuery.from(employee);

        Predicate predicate = employee.fname.toLowerCase().like("%" + search + "%")
                      .or(employee.lname.toLowerCase().like("%" + search + "%"))
                      .or(employee.mname.toLowerCase().like("%" + search + "%"))
                      .or(employee.serial.toLowerCase().like("%" + search + "%"));

        if(search != null){
            countQuery.where(predicate);
        }

        Long count = countQuery.count();

        // For fetching records
        jpaQuery = jpaQuery.from(employee)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if(search != null){
            jpaQuery.where(predicate);
        }

        List<Employee> employees = jpaQuery.createQuery(employee).getResultList();

        return  toPageInfo(new PageImpl<Employee>(employees, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));
    }

    /**
     * Get employee by id
     * @param employeeId
     * @return
     */
    public EmployeeInfo findById(String employeeId) {
        Employee employee = employeeDao.findById(employeeId);

        return toDto(employee);
    }

    /**
     * Add new employee
     * @param employee
     * @return
     */
    public EmployeeInfo add(Employee employee) {
        Employee savedEmployee = employeeDao.save(employee);

        return toDto(savedEmployee);
    }

    /**
     * Patch employee. Only save specific fields
     * @param tobePatchedEmployee
     * @return
     */
    public EmployeeInfo patch(Employee tobePatchedEmployee) {
        Employee employee = employeeDao.findById(tobePatchedEmployee.getId());

        if (employee != null) {
            // Convert to DTO
            EmployeeInfo employeeInfo = toDto(employee);

            // Map tobePatchedEmployee to employeeInfo, this will leave null values in destination untouched. See DataMappingConfig mapNull(fals)
            dataMapper.map(tobePatchedEmployee, employeeInfo);

            // Convert to entity so we can save to database
            Employee updatedEmployee = toEntity(employeeInfo);

            Employee savedEmployee = employeeDao.save(updatedEmployee);

            /*// Delete existing contacts
            Iterator<EmployeeContact> irExistingContacts = employee.getEmployeeContacts().iterator();
            while(irExistingContacts.hasNext()){
                employeeContactDao.delete(irExistingContacts.next().getId());
            }

            // Add the contacts
            Iterator<EmployeeContact> irNewContacts = tobePatchedEmployee.getEmployeeContacts().iterator();

            while(irNewContacts.hasNext()){
                EmployeeContact employeeContact = irNewContacts.next();
                employeeContact.setEmployee(updatedEmployee);

                // Add the ad
               // employeeContact.setBusinessEmployeeAddress();
               // employeeContact.setHomeEmployeeAddress();
                employeeContactDao.save(employeeContact);
            }

            // Delete existing projects
            Iterator<EmployeeProject> irExistingProjects = employee.getEmployeeProjects().iterator();
            while(irExistingProjects.hasNext()){
                employeeProjectDao.delete(irExistingProjects.next().getId());
            }

            // Add the projects
            Iterator<EmployeeProject> irNewProjects = tobePatchedEmployee.getEmployeeProjects().iterator();

            while(irNewProjects.hasNext()){
                EmployeeProject employeeProject = irNewProjects.next();
                employeeProject.setEmployee(updatedEmployee);

                employeeProjectDao.save(employeeProject);
            }*/

            return toDto(savedEmployee);
        }

        return null;
    }

    /**
     * Delete employee
     * @param employeeId
     */
    public EmployeeInfo delete(String employeeId) {

        Employee employee = employeeDao.findById(employeeId);

        employeeDao.delete(employeeId);

        //Delete linked data here...

        //TODO: create a standard message object to return to controller
        return toDto(employee);
    }

    /**
     * Find employee by email
     * @param email
     * @return
     */
    public EmployeeInfo findByEmail(String email) {
        return toDto(employeeDao.findByEmail(email));
    }

    /**
     * Get all people managers
     * @param search
     * @param pageable
     * @return
     */
    public PageInfo<EmployeeInfo> findPeopleManagers(String search, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);
        JPAQuery countQuery = jpaQuery.clone(entityManager);
        Predicate pManager = employee.pem.eq(true);
        Predicate pSearch = employee.fname.toLowerCase().like("%" + search + "%")
                .or(employee.lname.toLowerCase().like("%" + search + "%"))
                .or(employee.mname.toLowerCase().like("%" + search + "%"))
                .or(employee.serial.toLowerCase().like("%" + search + "%"));

        // For counting records
        countQuery = countQuery.from(employee).where(pManager);

        if(search != null){
            countQuery.where(pSearch);
        }

        Long count = countQuery.count();

        // For fetching records
        jpaQuery = jpaQuery.from(employee).where(pManager)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if(search != null){
            jpaQuery.where(pSearch);
        }

        List<Employee> employees = jpaQuery.createQuery(employee).getResultList();

        return  toPageInfo(new PageImpl<Employee>(employees, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));

    }

    /**
     * Get all delivery managers
     * @param search
     * @param pageable
     * @return
     */
    public PageInfo<EmployeeInfo> findDeliveryManagers(String search, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);
        JPAQuery countQuery = jpaQuery.clone(entityManager);
        Predicate pManager = employee.dm.eq(true);
        Predicate pSearch = employee.fname.toLowerCase().like("%" + search + "%")
                .or(employee.lname.toLowerCase().like("%" + search + "%"))
                .or(employee.mname.toLowerCase().like("%" + search + "%"))
                .or(employee.serial.toLowerCase().like("%" + search + "%"));

        // For counting records
        countQuery = countQuery.from(employee).where(pManager);

        if(search != null){
            countQuery.where(pSearch);
        }

        Long count = countQuery.count();

        // For fetching records
        jpaQuery = jpaQuery.from(employee).where(pManager)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if(search != null){
            jpaQuery.where(pSearch);
        }

        List<Employee> employees = jpaQuery.createQuery(employee).getResultList();

        return  toPageInfo(new PageImpl<Employee>(employees, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));

    }

    /**
     * Get all team leads
     * @param search
     * @param pageable
     * @return
     */
    public PageInfo<EmployeeInfo> findTeamLeads(String search, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);
        JPAQuery countQuery = jpaQuery.clone(entityManager);
        Predicate pTeamLead = employee.tl.eq(true);
        Predicate pSearch = employee.fname.toLowerCase().like("%" + search + "%")
                .or(employee.lname.toLowerCase().like("%" + search + "%"))
                .or(employee.mname.toLowerCase().like("%" + search + "%"))
                .or(employee.serial.toLowerCase().like("%" + search + "%"));

        // For counting records
        countQuery = countQuery.from(employee).where(pTeamLead);

        if(search != null){
            countQuery.where(pSearch);
        }

        Long count = countQuery.count();

        // For fetching records
        jpaQuery = jpaQuery.from(employee).where(pTeamLead)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if(search != null){
            jpaQuery.where(pSearch);
        }

        List<Employee> employees = jpaQuery.createQuery(employee).getResultList();

        return  toPageInfo(new PageImpl<Employee>(employees, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));

    }

    /**
     * Tag as lead
     * @param tobePatchedEmployee
     * @return
     */
    public EmployeeInfo tagAsTeamLead(Employee tobePatchedEmployee) {
        Employee employee = employeeDao.findById(tobePatchedEmployee.getId());
        employee.setTl(tobePatchedEmployee.isTl());

        return this.patch(employee);
    }

    /**
     * Tag as people manager
     * @param tobePatchedEmployee
     * @return
     */
    public EmployeeInfo tagAsPeopleManager(Employee tobePatchedEmployee) {
        Employee employee = employeeDao.findById(tobePatchedEmployee.getId());
        employee.setPem(tobePatchedEmployee.isPem());

        return this.patch(employee);
    }

    /**
     * Tag as delivery manager
     * @param tobePatchedEmployee
     * @return
     */
    public EmployeeInfo tagAsDeliveryManager(Employee tobePatchedEmployee) {
        Employee employee = employeeDao.findById(tobePatchedEmployee.getId());
        employee.setDm(tobePatchedEmployee.isDm());

        return this.patch(employee);
    }

    /**
     * Get employees by delivery manager
     * @param employeeId
     * @param search
     * @param pageable
     * @return
     */
    public PageInfo<EmployeeInfo> findEmployeesByDeliveryManager(String employeeId, String search, Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);
        JPAQuery countQuery = jpaQuery.clone(entityManager);

        jpaQuery = jpaQuery.from(employeeProject)
                .leftJoin(employeeProject.employee);

        Predicate pDeliveryManager = employeeProject.deliveryManager.id.eq(employeeId);
        Predicate pSearch = employeeProject.employee.fname.toLowerCase().like("%" + search + "%")
                .or(employeeProject.employee.lname.toLowerCase().like("%" + search + "%"))
                .or(employeeProject.employee.mname.toLowerCase().like("%" + search + "%"))
                .or(employeeProject.employee.serial.toLowerCase().like("%" + search + "%"));

        // For counting records
        countQuery = jpaQuery.where(pDeliveryManager);

        if(search != null){
            countQuery.where(pSearch);
        }

        Long count = countQuery.count();

        // For fetching records
        jpaQuery = jpaQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if(search != null){
            jpaQuery.where(pSearch);
        }

        List<Employee> employees = jpaQuery.createQuery(employeeProject.employee).getResultList();

        return  toPageInfo(new PageImpl<Employee>(employees, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));

    }
}
