package com.ibm.ph.edm.common.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Entity
@Table(name = "employee_group")
public class EmployeeGroup extends AbstractEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_added")
    public Date dateAdded;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "added_by", nullable = true)
    public Employee addedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated")
    public Date dateUpdated;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = true)
    public Employee updatedBy;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
