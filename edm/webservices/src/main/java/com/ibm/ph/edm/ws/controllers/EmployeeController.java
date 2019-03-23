package com.ibm.ph.edm.ws.controllers;

import com.ibm.extremeblue.w3java.W3JavaException;
import com.ibm.ph.edm.common.dto.EmployeeInfo;
import com.ibm.ph.edm.common.entities.Employee;
import com.ibm.ph.edm.common.services.W3Service;
import com.ibm.ph.edm.paging.PageInfo;
import com.ibm.ph.edm.common.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    private static Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @Autowired
    W3Service w3Service;

    /**
     * List all employees
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeInfo> list(@RequestParam(value = "search", required = false) String search, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employees.", this.getClass().getName());

        return employeeService.list(search, pageable);
    }

    /**
     * List all employees by delivery manager
     * @param pageable
     * @return
     */
    @RequestMapping(value="/delivery-manager/{employeeId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeInfo> getEmployeesByDeliveryManager(@PathVariable String employeeId, @RequestParam(value = "search", required = false) String search, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employees.", this.getClass().getName());

        return employeeService.findEmployeesByDeliveryManager(employeeId, search, pageable);
    }

    /**
     * Get employee by id
     * @param employeeId
     * @return
     */
    @RequestMapping(value="/{employeeId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeInfo get(@PathVariable String employeeId){
        LOG.info("Method get in class {} was called to get employee with id {}.", this.getClass().getName(), employeeId);

        return employeeService.findById(employeeId);
    }

    /**
     * Add new employee
     * @param employee
     *
     * Example payload:
     * {"lname" : "Adorna", "fname" : "Jaygee", "mname" : "", "email" : "adornaj@ph.ibm.com", "serial" : "xxxx"}
     *
     * {"lname" : "Navarro", "fname" : "William", "mname" : "", "email" : "navarrw@ph.ibm.com", "serial" : "xxxx", "peopleManager" : {"id" : "ID HERE"}}
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PEM')")
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeInfo add(@RequestBody Employee employee){
        LOG.info("Method add() in class {} was called to add new employee.", this.getClass().getName());

        return employeeService.add(employee);
    }

    /**
     * Update employee
     * @param employeeId
     * @param employee
     *
     * Example payload:
     * {"lname" : "Adorna", "fname" : "Jaygee", "mname" : "", "email" : "adornaj@ph.ibm.com", "serial" : "xxxx"}
     *
     * {"lname" : "Navarro", "fname" : "William", "mname" : "", "email" : "navarrw@ph.ibm.com", "serial" : "xxxx", "peopleManager" : {"id" : "ID HERE"}}
     */
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeInfo update(@PathVariable String employeeId, @RequestBody Employee employee){
        LOG.info("Method update() in class {} was called to update employee with id {}.", this.getClass().getName(), employeeId);

        // Set the id to make sure JPA will update
        employee.setId(employeeId);

        return employeeService.patch(employee);
    }

    /**
     * Delete employee
     * @param employeeId
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeInfo delete(@PathVariable String employeeId){
        LOG.info("Method delete() in class {} was called to delete employee with id {}.", this.getClass().getName(), employeeId);

       return employeeService.delete(employeeId);
    }

    /**
     * List all people managers
     * @param pageable
     * @return
     */
    @RequestMapping(value="/people-managers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeInfo> listPeopleManagers(@RequestParam(value = "search", required = false) String search, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employees.", this.getClass().getName());

        return employeeService.findPeopleManagers(search, pageable);
    }

    /**
     * List all delivery managers
     * @param pageable
     * @return
     */
    @RequestMapping(value="/delivery-managers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeInfo> listDeliverManagers(@RequestParam(value = "search", required = false) String search, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employees.", this.getClass().getName());

        return employeeService.findDeliveryManagers(search, pageable);
    }

    /**
     * List all team leads
     * @param pageable
     * @return
     */
    @RequestMapping(value="/team-leads", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeInfo> listTeamLeads(@RequestParam(value = "search", required = false) String search, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employees.", this.getClass().getName());

        return employeeService.findTeamLeads(search, pageable);
    }

    /**
     * Get employee from w3
     * @param email
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/w3", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeInfo getEmployeeFromW3(@RequestParam(value = "email") String email){
        LOG.info("Method get in class {} was called to get employee with email {}.", this.getClass().getName(), email);

        EmployeeInfo employeeInfo = employeeService.findByEmail(email);

        try {
            if(employeeInfo == null){
                employeeInfo = w3Service.getEmployeeFromW3(email);
                employeeInfo.setEmail(email);
                employeeInfo.setId("1");
            }

            return employeeInfo;
        } catch (W3JavaException e) {
            employeeInfo = new EmployeeInfo();
            employeeInfo.setEmail(email);
            employeeInfo.setId(null);//Put null to let the user not found

            return employeeInfo;
        }
    }

    /**
     * Get employee from w3
     * @param employee
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/w3", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeInfo addFromW3(@RequestBody Employee employee){
        LOG.info("Method get in class {} was called to get employee with email {}.", this.getClass().getName(), employee.getEmail());
        EmployeeInfo employeeInfo;

        try {
            return w3Service.getOrCreateEmployeeFromW3(employee.getEmail(), employee.isTl(), employee.isDm(), employee.isPem());
        } catch (W3JavaException e) {
            employeeInfo = new EmployeeInfo();
            employeeInfo.setEmail(employee.getEmail());
            employeeInfo.setId(null);//Put null to let the user not found

            return employeeInfo;
        }
    }

    /**
     * Tag/Untag as lead
     * @param employeeId
     * @param employee
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{employeeId}/tag-as-team-lead", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeInfo tagAsLead(@PathVariable String employeeId, @RequestBody Employee employee){
        LOG.info("Method update() in class {} was called to update employee with id {}.", this.getClass().getName(), employeeId);

        // Set the id to make sure JPA will update
        employee.setId(employeeId);

        return employeeService.tagAsTeamLead(employee);
    }

    /**
     * Tag/Untag as people manager
     * @param employeeId
     * @param employee
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{employeeId}/tag-as-people-manager", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeInfo tagAsPeopleManager(@PathVariable String employeeId, @RequestBody Employee employee){
        LOG.info("Method update() in class {} was called to update employee with id {}.", this.getClass().getName(), employeeId);

        // Set the id to make sure JPA will update
        employee.setId(employeeId);

        return employeeService.tagAsPeopleManager(employee);
    }

    /**
     * Tag/Untag as people manager
     * @param employeeId
     * @param employee
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{employeeId}/tag-as-delivery-manager", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeInfo tagAsDeliveryManager(@PathVariable String employeeId, @RequestBody Employee employee){
        LOG.info("Method update() in class {} was called to update employee with id {}.", this.getClass().getName(), employeeId);

        // Set the id to make sure JPA will update
        employee.setId(employeeId);

        return employeeService.tagAsDeliveryManager(employee);
    }
}
