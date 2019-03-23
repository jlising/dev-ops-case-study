package com.ibm.ph.edm.common.dto;

import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class RoleInfo {
    private String id;
    private String name;

    public Date dateAdded;

    public EmployeeBasicInfo addedBy;

    public Date dateUpdated;

    public EmployeeBasicInfo updatedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
