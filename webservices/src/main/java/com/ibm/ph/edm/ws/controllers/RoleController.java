package com.ibm.ph.edm.ws.controllers;

import com.ibm.ph.edm.common.dto.RoleInfo;
import com.ibm.ph.edm.common.entities.Role;
import com.ibm.ph.edm.paging.PageInfo;
import com.ibm.ph.edm.common.services.RoleService;
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
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = "/role")
public class RoleController {
    private static Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    /**
     * List all roles
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PageInfo<RoleInfo> list(Pageable pageable){
        LOG.info("Method list() in class {} was called to list all roles.", this.getClass().getName());

        return roleService.list(pageable);
    }

    /**
     * Get role by id
     * @param roleId
     * @return
     */
    @RequestMapping(value="/{roleId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RoleInfo get(@PathVariable String roleId){
        LOG.info("Method get in class {} was called to get role with id {}.", this.getClass().getName(), roleId);

        return roleService.findById(roleId);
    }

    /**
     * Add new role
     * @param role
     *
     * Example payload:
     * {"name" : "Administrator"}
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RoleInfo add(@RequestBody Role role){

        return roleService.add(role);
    }

    /**
     * Update role
     * @param roleId
     * @param role
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RoleInfo update(@PathVariable String roleId, @RequestBody Role role){
        LOG.info("Method update() in class {} was called to update role with id {}.", this.getClass().getName(), roleId);

        // Set the id to make sure JPA will update
        role.setId(roleId);


        return roleService.patch(role);
    }

    /**
     * Delete role
     * @param roleId
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void delete(@PathVariable String roleId){
        LOG.info("Method delete() in class {} was called to delete role with id {}.", this.getClass().getName(), roleId);

        roleService.delete(roleId);
    }
}
