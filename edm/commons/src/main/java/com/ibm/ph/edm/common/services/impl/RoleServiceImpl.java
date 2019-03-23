package com.ibm.ph.edm.common.services.impl;

import com.ibm.ph.edm.common.dao.RoleDao;
import com.ibm.ph.edm.common.dto.RoleInfo;
import com.ibm.ph.edm.common.entities.Role;
import com.ibm.ph.edm.paging.MappableService;
import com.ibm.ph.edm.paging.PageInfo;
import com.ibm.ph.edm.common.services.RoleService;
import com.mysema.query.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ibm.ph.edm.common.entities.QRole.role;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Service
public class RoleServiceImpl extends MappableService<RoleInfo, Role> implements RoleService {
    private static Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    RoleDao roleDao;

    /**
     * Get the list of roles
     * @param pageable
     * @return
     */
    public PageInfo<RoleInfo> list(Pageable pageable) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        JPAQuery countQuery = jpaQuery.clone(entityManager);
        Long count = countQuery.from(role).count();

        List<Role> roles = jpaQuery.from(role)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .createQuery(role).getResultList();

        return  toPageInfo(new PageImpl<Role>(roles, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), count));
    }

    /**
     * Get role by id
     * @param roleId
     * @return
     */
    public RoleInfo findById(String roleId) {
        Role role = roleDao.findById(roleId);

        return toDto(role);
    }

    /**
     * Add new role
     * @param role
     * @return
     */
    public RoleInfo add(Role role) {

        Role savedRole = roleDao.save(role);
        return toDto(savedRole);
    }

    /**
     * Patch role
     * @param tobePatchedRole
     * @return
     */
    public RoleInfo patch(Role tobePatchedRole) {

        Role savedRole = roleDao.save(tobePatchedRole);
        return toDto(savedRole);
    }

    /**
     * Delete role
     * @param roleId
     */
    public void delete(String roleId) {
        roleDao.delete(roleId);

        //Delete linked data here...
    }
}
