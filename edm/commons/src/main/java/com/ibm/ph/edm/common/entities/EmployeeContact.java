package com.ibm.ph.edm.common.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Entity
@Table(name = "employee_contact")
public class EmployeeContact extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private int priority; //1 - primary, 2 - secondary, etc..

    @Column(name = "email_address")
    private String emailAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_address_id", nullable = true)
    private EmployeeAddress homeEmployeeAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_address_id", nullable = true)
    private EmployeeAddress businessEmployeeAddress;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "home_phone_number")
    private String homePhoneNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_added")
    public Date dateAdded;

    @OneToOne
    @JoinColumn(name = "added_by", nullable = true)
    public Employee addedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated")
    public Date dateUpdated;

    @OneToOne
    @JoinColumn(name = "updated_by", nullable = true)
    public Employee updatedBy;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public EmployeeAddress getHomeEmployeeAddress() {
        return homeEmployeeAddress;
    }

    public void setHomeEmployeeAddress(EmployeeAddress homeEmployeeAddress) {
        this.homeEmployeeAddress = homeEmployeeAddress;
    }

    public EmployeeAddress getBusinessEmployeeAddress() {
        return businessEmployeeAddress;
    }

    public void setBusinessEmployeeAddress(EmployeeAddress businessEmployeeAddress) {
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

    public Employee getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Employee addedBy) {
        this.addedBy = addedBy;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Employee updatedBy) {
        this.updatedBy = updatedBy;
    }
}
