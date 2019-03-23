package com.ibm.ph.edm.common.dao;

import com.ibm.ph.edm.common.entities.EmployeeContact;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface EmployeeContactDao extends BaseJpaDao<EmployeeContact, String>,  EmployeeContactDaoCustom{
}
