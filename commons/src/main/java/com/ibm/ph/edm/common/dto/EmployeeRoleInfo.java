package com.ibm.ph.edm.common.dto;

import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class EmployeeRoleInfo {
    private RoleInfo role;
    public Date dateAdded;

    public EmployeeBasicInfo addedBy;
    public Date dateUpdated;
    public EmployeeBasicInfo updatedBy;

    public RoleInfo getRole() {
        return role;
    }

    public void setRole(RoleInfo role) {
        this.role = role;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public EmployeeBasicInfo getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(EmployeeBasicInfo addedBy) {
        this.addedBy = addedBy;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public EmployeeBasicInfo getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(EmployeeBasicInfo updatedBy) {
        this.updatedBy = updatedBy;
    }
}
