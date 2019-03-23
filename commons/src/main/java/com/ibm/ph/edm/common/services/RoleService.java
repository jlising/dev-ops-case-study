package com.ibm.ph.edm.common.services;

import com.ibm.ph.edm.common.dto.RoleInfo;
import com.ibm.ph.edm.common.entities.Role;
import com.ibm.ph.edm.paging.PageInfo;
import org.springframework.data.domain.Pageable;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface RoleService {
    PageInfo<RoleInfo> list(Pageable pageable);
    RoleInfo findById(String roleId);

    RoleInfo add(Role role);
    RoleInfo patch(Role tobePatchedRole);
    void delete(String roleId);
}
