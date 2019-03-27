package com.ibm.ph.edm.ws.controllers;

import com.ibm.ph.edm.common.dto.ActionItemInfo;
import com.ibm.ph.edm.common.dto.EmployeeActionItemInfo;
import com.ibm.ph.edm.common.dto.EmployeeActionItemSelectionInfo;
import com.ibm.ph.edm.common.entities.ActionItem;
import com.ibm.ph.edm.common.entities.EmployeeActionItem;
import com.ibm.ph.edm.common.services.ActionItemService;
import com.ibm.ph.edm.common.services.EmployeeActionItemService;
import com.ibm.ph.edm.paging.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@RestController
@RequestMapping(value = "/action-item")
public class ActionItemController {

    private static Logger LOG = LoggerFactory.getLogger(ActionItemController.class);

    @Autowired
    ActionItemService actionItemService;

    @Autowired
    EmployeeActionItemService employeeActionItemService;

    /**
     * List all action items
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<ActionItemInfo> list(@RequestParam(value = "search", required = false) String search, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employees.", this.getClass().getName());

        return actionItemService.list(search, pageable);
    }

    /**
     * Add action item
     * @param actionItem
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PEM')")
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ActionItemInfo add(@RequestBody ActionItem actionItem){
        LOG.info("Method add() in class {} was called to add new action item.", this.getClass().getName());

        return actionItemService.add(actionItem);
    }

    /**
     * Get employee by id
     * @param actionItemId
     * @return
     */
    @RequestMapping(value="/{actionItemId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ActionItemInfo get(@PathVariable String actionItemId){
        LOG.info("Method get in class {} was called to get action item with id {}.", this.getClass().getName(), actionItemId);

        return actionItemService.findById(actionItemId);
    }

    /**
     * Delete employee
     * @param actionItemId
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{actionItemId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ActionItemInfo delete(@PathVariable String actionItemId){
        LOG.info("Method delete() in class {} was called to delete action item with id {}.", this.getClass().getName(), actionItemId);

        return actionItemService.delete(actionItemId);
    }

    /**
     * Tag/Untag as lead
     * @param actionItemId
     * @param actionItem
     * @return
     */
    @RequestMapping(value = "/{actionItemId}", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ActionItemInfo update(@PathVariable String actionItemId, @RequestBody ActionItem actionItem){
        LOG.info("Method update() in class {} was called to update action item with id {}.", this.getClass().getName(), actionItemId);

        // Set the id to make sure JPA will update
        actionItem.setId(actionItemId);

        return actionItemService.patch(actionItem);
    }

    /**
     * Assign action item
     * @param actionItemId
     * @param employeeIds
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PEM')")
    @RequestMapping(value = "/{actionItemId}/assign",method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<EmployeeActionItemInfo> assign(@PathVariable String actionItemId, @RequestBody List<EmployeeActionItemSelectionInfo> employeeActionItemSelections){
        LOG.info("Method assign() in class {} was called to assign action item.", this.getClass().getName());

        return employeeActionItemService.assign(actionItemId, employeeActionItemSelections);
    }

    /**
     * Get employees by action item
     * @param actionItemId
     * @param search
     * @param pageable
     * @return
     */
    @RequestMapping(value="/{actionItemId}/assigned", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeActionItemInfo> list(@PathVariable String actionItemId, @RequestParam(value = "search", required = false) String search, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employees.", this.getClass().getName());

        return employeeActionItemService.findAssignedEmployeesByActionItemId(actionItemId, search, pageable);
    }
}
