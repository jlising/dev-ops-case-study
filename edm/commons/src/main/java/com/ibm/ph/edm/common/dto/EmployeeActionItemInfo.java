package com.ibm.ph.edm.common.dto;

import com.ibm.ph.edm.common.entities.AbstractEntity;
import com.ibm.ph.edm.common.entities.ActionItem;
import com.ibm.ph.edm.common.entities.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
public class EmployeeActionItemInfo extends AbstractEntity{

    private String id;
    private EmployeeBasicInfo employee;
    private ActionItemInfo actionItem;
    public Date dateAdded;
    public Date dateDue;
    public Date dateCompleted;
    public String completionRemarks;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public EmployeeBasicInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeBasicInfo employee) {
        this.employee = employee;
    }

    public ActionItemInfo getActionItem() {
        return actionItem;
    }

    public void setActionItem(ActionItemInfo actionItem) {
        this.actionItem = actionItem;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getCompletionRemarks() {
        return completionRemarks;
    }

    public void setCompletionRemarks(String completionRemarks) {
        this.completionRemarks = completionRemarks;
    }
}
