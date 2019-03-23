package com.ibm.ph.edm.common.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Entity
@Table(name = "employee_reimbursement")
public class EmployeeReimbursement extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "broad_band")
    private Boolean broadBand;

    @Column(name = "mobile_data")
    private Boolean mobileData;

    @Column(name = "mobile_device")
    private Boolean mobileDevice;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="employee_project_id")
    private EmployeeProject employeeProject;

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

    public EmployeeProject getEmployeeProject() {
        return employeeProject;
    }

    public void setEmployeeProject(EmployeeProject employeeProject) {
        this.employeeProject = employeeProject;
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
