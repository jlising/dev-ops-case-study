package com.ibm.ph.edm.common.dto;

import com.ibm.ph.edm.common.entities.Employee;

import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class EmployeeContactInfo {
    private String id;

    private EmployeeBasicInfo employee;

    private int priority;
    private String emailAddress;
    private EmployeeAddressInfo homeEmployeeAddress;
    private EmployeeAddressInfo businessEmployeeAddress;
    private String mobileNumber;
    private String homePhoneNumber;

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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EmployeeAddressInfo getHomeEmployeeAddress() {
        return homeEmployeeAddress;
    }

    public void setHomeEmployeeAddress(EmployeeAddressInfo homeEmployeeAddress) {
        this.homeEmployeeAddress = homeEmployeeAddress;
    }

    public EmployeeAddressInfo getBusinessEmployeeAddress() {
        return businessEmployeeAddress;
    }

    public void setBusinessEmployeeAddress(EmployeeAddressInfo businessEmployeeAddress) {
        this.businessEmployeeAddress = businessEmployeeAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
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

    public EmployeeBasicInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeBasicInfo employee) {
        this.employee = employee;
    }
}
