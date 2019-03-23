package com.ibm.ph.edm.ws.config;

import com.ibm.ph.edm.common.dto.EmployeeInfo;
import com.ibm.ph.edm.common.dto.RoleInfo;
import com.ibm.ph.edm.common.entities.Employee;
import com.ibm.ph.edm.common.entities.Role;
import com.ibm.ph.edm.paging.DataMapper;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Configuration
public class DataMapping {
    @Autowired
    private DataMapper dataMapper;

    @PostConstruct
    public void init() {

       MapperFactory mapperFactory = dataMapper.getMapperFactory();

       mapperFactory.classMap(Employee.class, EmployeeInfo.class)
                //.field("roles", "roleInfos")

                .byDefault()
                .register();

        mapperFactory.classMap(Role.class, RoleInfo.class)
                .byDefault()
                .register();
    }
}
