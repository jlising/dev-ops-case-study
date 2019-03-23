package com.ibm.ph.edm.common.dto;

import com.ibm.ph.edm.common.entities.Employee;

import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class EmployeeReimbursementInfo {
    private String id;

    private EmployeeBasicInfo employee;

    private Boolean broadBand;
    private Boolean mobileData;
    private Boolean mobileDevice;

    private EmployeeProjectBaseInfo employeeProject;

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

    public EmployeeBasicInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeBasicInfo employee) {
        this.employee = employee;
    }

    public Boolean getBroadBand() {
        return broadBand;
    }

    public void setBroadBand(Boolean broadBand) {
        this.broadBand = broadBand;
    }

    public Boolean getMobileData() {
        return mobileData;
    }

    public void setMobileData(Boolean mobileData) {
        this.mobileData = mobileData;
    }

    public Boolean getMobileDevice() {
        return mobileDevice;
    }

    public void setMobileDevice(Boolean mobileDevice) {
        this.mobileDevice = mobileDevice;
    }

    public EmployeeProjectBaseInfo getEmployeeProject() {
        return employeeProject;
    }

    public void setEmployeeProject(EmployeeProjectBaseInfo employeeProject) {
        this.employeeProject = employeeProject;
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
