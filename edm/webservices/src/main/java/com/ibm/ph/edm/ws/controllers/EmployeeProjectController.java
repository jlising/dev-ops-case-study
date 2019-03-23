package com.ibm.ph.edm.ws.controllers;

import com.ibm.ph.edm.common.dto.EmployeeProjectInfo;
import com.ibm.ph.edm.common.dto.EmployeeReimbursementInfo;
import com.ibm.ph.edm.common.entities.EmployeeProject;
import com.ibm.ph.edm.common.services.EmployeeReimbursementService;
import com.ibm.ph.edm.paging.PageInfo;
import com.ibm.ph.edm.common.services.EmployeeProjectService;
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
@RequestMapping(value = "/employee/{employeeId}/project")
public class EmployeeProjectController {

    private static Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeProjectService employeeProjectService;

    @Autowired
    EmployeeReimbursementService employeeReimbursementService;

    /**
     * List all employee projects
     * @param employeeId
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeProjectInfo> list(@PathVariable (value ="employeeId") String employeeId, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employee's projects.", this.getClass().getName());

        return employeeProjectService.list(employeeId, pageable);
    }

    /**
     * Get employee project by id
     * @param employeeProjectId
     * @return
     */
    @RequestMapping(value="/{employeeProjectId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeProjectInfo get(@PathVariable String employeeProjectId){
        LOG.info("Method get in class {} was called to get employee's project with id {}.", this.getClass().getName(), employeeProjectId);

        return employeeProjectService.findById(employeeProjectId);
    }

    /**
     * Add new employee project
     * @param employeeId
     * @param employeeProject
     *
     * Example payload:
     * {"employee" : {"id": "89328a2b5fdebb2e015fdebbc5ed0000"}, "teamName": "TEAM NAME HERE", "teamLead": {"id": "89328a2b5fdeb156015fdeb641880001"},"deliveryManager": {"id": "89328a2b5fdeb156015fdeb641880001"}, "shift": "3", "onCallSchedule": 0,"totalOnCallHours": "0"}
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeProjectInfo add(@PathVariable(value="employeeId") String employeeId, @RequestBody EmployeeProject employeeProject){
        LOG.info("Method add() in class {} was called to add new employee.", this.getClass().getName());

        employeeProject.getEmployee().setId(employeeId);

        return employeeProjectService.add(employeeProject);
    }

    /**
     * Update employee project
     * @param employeeId
     * @param employeeProjectId
     * @param employeeProject
     *
     * Example payload:
     * {"employee" : {"id": "89328a2b5fdebb2e015fdebbc5ed0000"}, "teamName": "TEAM NAME HERE", "teamLead": {"id": "89328a2b5fdeb156015fdeb641880001"},"deliveryManager": {"id": "89328a2b5fdeb156015fdeb641880001"}, "shift": "3", "onCallSchedule": 0,"totalOnCallHours": "0"}
     */
    @RequestMapping(value = "/{employeeProjectId}", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeProjectInfo update(@PathVariable(value = "employeeId") String employeeId, @PathVariable String employeeProjectId, @RequestBody EmployeeProject employeeProject){
        LOG.info("Method update() in class {} was called to update employee's project with id {}.", this.getClass().getName(), employeeProjectId);

        // Set the id to make sure JPA will update
        employeeProject.getEmployee().setId(employeeId);
        employeeProject.setId(employeeProjectId);

        return employeeProjectService.patch(employeeProject);
    }

    /**
     * Delete employee project
     * @param employeeProjectId
     */
    @RequestMapping(value = "/{employeeProjectId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void delete(@PathVariable String employeeProjectId){
        LOG.info("Method delete() in class {} was called to delete employee's project with id {}.", this.getClass().getName(), employeeProjectId);

        employeeProjectService.delete(employeeProjectId);
    }

    @RequestMapping(value="/priority", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeProjectInfo getProjectByPriority(@PathVariable(value ="employeeId") String employeeId,@RequestParam(value="priority") int priority){
        LOG.info("Method get in class {} was called to get employee's {} primary project.", employeeId, this.getClass().getName());

        return employeeProjectService.findEmployeeProjectByPriority(employeeId, priority);
    }

    /**
     * Get employee reimbursements by employee project
     * @param employeeId
     * @param priority
     * @return
     */
    @RequestMapping(value="/priority/reimbursement", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EmployeeReimbursementInfo getReimbursementsByProjectId(@PathVariable(value ="employeeId") String employeeId, @RequestParam(value="priority") int priority){
        LOG.info("Method get in class {} was called to get employee's {} primary project.", employeeId, this.getClass().getName());

        return employeeReimbursementService.findReimbursementsByEmployeeProjectAndPriority(employeeId, priority);
    }
}
