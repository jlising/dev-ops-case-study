package com.ibm.ph.edm.common.dao;

import com.ibm.ph.edm.common.entities.EmployeeAddress;

import java.util.List;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface EmployeeAddressDao extends BaseJpaDao<EmployeeAddress, String>, EmployeeAddressDaoCustom {
    List<EmployeeAddress> findByEmployeeId(String id);
}
