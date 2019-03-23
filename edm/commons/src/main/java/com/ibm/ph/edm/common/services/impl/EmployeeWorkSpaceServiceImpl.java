package com.ibm.ph.edm.common.services.impl;

import com.ibm.ph.edm.common.dao.EmployeeWorkSpaceDao;
import com.ibm.ph.edm.common.services.EmployeeWorkSpaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@Service
public class EmployeeWorkSpaceServiceImpl implements EmployeeWorkSpaceService{
        private static Logger LOG = LoggerFactory.getLogger(EmployeeWorkSpaceServiceImpl.class);

        @Autowired
        EntityManager entityManager;

        @Autowired
        EmployeeWorkSpaceDao employeeWorkSpaceDao;
}
