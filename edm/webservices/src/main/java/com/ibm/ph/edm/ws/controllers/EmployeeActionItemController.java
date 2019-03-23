package com.ibm.ph.edm.ws.controllers;

import com.ibm.ph.edm.common.dto.EmployeeActionItemInfo;
import com.ibm.ph.edm.common.services.EmployeeActionItemService;
import com.ibm.ph.edm.paging.PageInfo;
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
@RequestMapping(value = "/employee/{employeeId}/action-items")
public class EmployeeActionItemController {

    private static Logger LOG = LoggerFactory.getLogger(EmployeeActionItemController.class);

    @Autowired
    EmployeeActionItemService employeeActionItemService;

    /**
     * List all employee projects
     * @param employeeId
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<EmployeeActionItemInfo> list(@PathVariable(value ="employeeId") String employeeId, @RequestParam(value = "search", required = false) String search, Pageable pageable){
        LOG.info("Method list() in class {} was called to list all employee's projects.", this.getClass().getName());

        return employeeActionItemService.findActionItemsByEmployeeId(employeeId, search, pageable);
    }
}
