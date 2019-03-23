package com.ibm.ph.edm.ws.controllers;

import com.ibm.ph.edm.common.dto.EmployeeReimbursementInfo;
import com.ibm.ph.edm.common.entities.EmployeeReimbursement;
import com.ibm.ph.edm.paging.PageInfo;
import com.ibm.ph.edm.common.services.EmployeeReimbursementService;
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
@RequestMapping(value = "/employee/{employeeId}/reimbursement")
public class EmployeeReimbursementController {
    private static Logger LOG = LoggerFactory.getLogger(EmployeeReimbursementController.class);

    @Autowired
    EmployeeReimbursementService employeeReimbursementService;

    /**
     * List all employee reimbursements
     * @param employeeId
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeReimbursementInfo> list(@PathVariable(value = "employeeId") String employeeId, Pageable pageable) {
        LOG.info("Method list() in class {} was called to list all employee's reimbursement.", this.getClass().getName());

        return employeeReimbursementService.list(employeeId, pageable);
    }

    /**
     * Get employee reimbursement by id
     * @param employeeReimbursementId
     * @return
     */
    @RequestMapping(value = "/{employeeReimbursementId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeReimbursementInfo get(@PathVariable String employeeReimbursementId) {
        LOG.info("Method get in class {} was called to get employee's reimbursement with id {}.", this.getClass().getName(), employeeReimbursementId);

        return employeeReimbursementService.findById(employeeReimbursementId);
    }

    /**
     * Add new employee reimbursement
     * @param employeeId
     * @param employeeReimbursement
     *
     * Example payload:
     * {"broadBand": true, "mobileData": true, "mobileDevice": false, "employeeProject": { "id" : "402896875fcae2ee015fcae313770000"}}
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeReimbursementInfo add(@PathVariable(value ="employeeId") String employeeId, @RequestBody EmployeeReimbursement employeeReimbursement) {
        LOG.info("Method add() in class {} was called to add new employee reimbursement.", this.getClass().getName());

        employeeReimbursement.getEmployee().setId(employeeId);


        return employeeReimbursementService.add(employeeReimbursement);
    }

    /**
     * Update employee reimbursement
     * @param employeeId
     * @param employeeReimbursementId
     * @param employeeReimbursement
     *
     * Example payload:
     * {"broadBand": true, "mobileData": true, "mobileDevice": false, "employeeProject": { "id" : "402896875fcae2ee015fcae313770000"}}
     */
    @RequestMapping(value = "/{employeeReimbursementId}", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeReimbursementInfo update(@PathVariable(value ="employeeId") String employeeId, @PathVariable String employeeReimbursementId, @RequestBody EmployeeReimbursement employeeReimbursement) {
        LOG.info("Method add() in class {} was called to add new employee reimbursement.", this.getClass().getName());

        // Set the id to make sure JPA will update
        employeeReimbursement.getEmployee().setId(employeeId);
        employeeReimbursement.setId(employeeReimbursementId);


        return employeeReimbursementService.patch(employeeReimbursement);
    }


    /**
     * Delete employee reimbursement
     * @param employeeReimbursementId
     */
    @RequestMapping(value = "/{employeeReimbursementId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void delete(@PathVariable String employeeReimbursementId) {
        LOG.info("Method delete() in class {} was called to delete employee's reimbursement with id {}.", this.getClass().getName(), employeeReimbursementId);

        employeeReimbursementService.delete(employeeReimbursementId);
    }
}
