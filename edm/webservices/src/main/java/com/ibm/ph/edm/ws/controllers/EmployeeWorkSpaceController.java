package com.ibm.ph.edm.ws.controllers;

import com.ibm.ph.edm.common.services.EmployeeWorkSpaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@RestController
@RequestMapping(value = "/employee/{employeeId}/workspace")
public class EmployeeWorkSpaceController {
    private static Logger LOG = LoggerFactory.getLogger(EmployeeWorkSpaceController.class);

    @Autowired
    EmployeeWorkSpaceService employeeWorkSpaceService;

}
