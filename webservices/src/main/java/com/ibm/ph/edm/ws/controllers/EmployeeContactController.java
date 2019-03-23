package com.ibm.ph.edm.ws.controllers;

import com.ibm.ph.edm.common.dao.EmployeeDao;
import com.ibm.ph.edm.common.dto.EmployeeContactInfo;
import com.ibm.ph.edm.common.entities.EmployeeContact;
import com.ibm.ph.edm.paging.PageInfo;
import com.ibm.ph.edm.common.services.EmployeeContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@RestController
@RequestMapping(value = "/employee/{employeeId}/contact")
public class EmployeeContactController {
    private static Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeContactService employeeContactService;

    @Autowired
    EmployeeDao employeeDao;

    /**
     * List all employee contacts
     * @param employeeId
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeContactInfo> list(@PathVariable(value ="employeeId") String employeeId, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employee's contacts.", this.getClass().getName());

        return employeeContactService.list(employeeId, pageable);
    }

    /**
     * Get employee contact by id
     * @param employeeContactId
     * @return
     */
    @RequestMapping(value="/{employeeContactId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeContactInfo get(@PathVariable String employeeContactId){
        LOG.info("Method get in class {} was called to get employee's contact with id {}.", this.getClass().getName(), employeeContactId);

        return employeeContactService.findById(employeeContactId);
    }

    /**
     * Add new employee contact
     * @param employeeId
     * @param employeeContact
     *
     * Example payload:
     * {"employee" : {"id" : "89328a2b5fdebb2e015fdebbc5ed0000"}, "priority": "1", "emailAddress": "lisingj@ph.ibm.com", "homeEmployeeAddress": { "employee": {"id": "402896875fcab24a015fcabb98c60007"}, "address1": "118 Purok 2", "address2": "Lacmit", "city": "Arayat, Pampanga", "country": "Philippines", "state": "", "zip": "2012" }, "businessEmployeeAddress": { "employee": {"id": "402896875fcab24a015fcabb98c60007"}, "address1": "118 Purok 2", "address2": "Lacmit", "city": "Arayat, Pampanga", "country": "Philippines", "state": "", "zip": "2012" }, "mobileNumber": "+63923 210 4978", "homePhoneNumber": "+63923 210 4978"}
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeContactInfo add(@PathVariable(value ="employeeId") String employeeId, @RequestBody EmployeeContact employeeContact){
        LOG.info("Method add() in class {} was called to add new employee contact.", this.getClass().getName());

        employeeContact.getEmployee().setId(employeeId);

        return employeeContactService.add(employeeContact);
    }

    /**
     * Update employee contact
     * @param employeeId
     * @param employeeContactId
     * @param employeeContact
     *
     * Example payload:
     * {"employee" : {"id" : "89328a2b5fdebb2e015fdebbc5ed0000"}, "priority": "1", "emailAddress": "lisingj@ph.ibm.com", "homeEmployeeAddress": { "employee": {"id": "402896875fcab24a015fcabb98c60007"}, "address1": "118 Purok 2", "address2": "Lacmit", "city": "Arayat, Pampanga", "country": "Philippines", "state": "", "zip": "2012" }, "businessEmployeeAddress": { "employee": {"id": "402896875fcab24a015fcabb98c60007"}, "address1": "118 Purok 2", "address2": "Lacmit", "city": "Arayat, Pampanga", "country": "Philippines", "state": "", "zip": "2012" }, "mobileNumber": "+63923 210 4978", "homePhoneNumber": "+63923 210 4978"}
     */
    @RequestMapping(value = "/{employeeContactId}", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeContactInfo update(@PathVariable(value ="employeeId") String employeeId, @PathVariable String employeeContactId, @RequestBody EmployeeContact employeeContact){
        LOG.info("Method update() in class {} was called to update employee's contact with id {}.", this.getClass().getName(), employeeContactId);

        // Set the id to make sure JPA will update
        employeeContact.setId(employeeContactId);
        employeeContact.getEmployee().setId(employeeId);

        return employeeContactService.patch(employeeContact);
    }

    /**
     * Delete employee contact
     * @param employeeContactId
     */
    @RequestMapping(value = "/{employeeContactId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void delete(@PathVariable String employeeContactId){
        LOG.info("Method delete() in class {} was called to delete employee's contact with id {}.", this.getClass().getName(), employeeContactId);

        employeeContactService.delete(employeeContactId);
    }

    /**
     * Get employee primary contact by id
     * @param employeeId
     * @return
     */
    @RequestMapping(value="/priority", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeContactInfo getContactByPriority(@PathVariable(value ="employeeId") String employeeId,@RequestParam(value="priority") int priority){
        LOG.info("Method get in class {} was called to get employee's {} primary contact.", employeeId, this.getClass().getName());

        return employeeContactService.findEmployeeContactByPriority(employeeId, priority);
    }
}
