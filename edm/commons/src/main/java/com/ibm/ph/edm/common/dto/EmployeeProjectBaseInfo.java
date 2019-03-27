package com.ibm.ph.edm.common.dto;

import com.ibm.ph.edm.common.entities.BCPRole;
import com.ibm.ph.edm.common.entities.Capped;
import com.ibm.ph.edm.common.entities.OnCallSchedule;
import com.ibm.ph.edm.common.entities.Shift;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class EmployeeProjectBaseInfo {
    private String id;
    private String teamName;
    private EmployeeBasicInfo teamLead;
    private EmployeeBasicInfo deliveryManager;
    private int priority; //1 - primary, 2 - secondary, etc..
    private EmployeeWorkSpaceInfo employeeWorkSpace;

    private Shift shift;
    private OnCallSchedule onCallSchedule;
    private float totalOnCallHours;

    private BCPRole bcpRole;
    private Boolean criticalServiceSupported;
    private EmployeeBasicInfo employeeBackup;
    private String projectEmailAddress;
    private Capped capped;
    private float cappedHours;

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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public EmployeeBasicInfo getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(EmployeeBasicInfo teamLead) {
        this.teamLead = teamLead;
    }

    public EmployeeBasicInfo getDeliveryManager() {
        return deliveryManager;
    }

    public void setDeliveryManager(EmployeeBasicInfo deliveryManager) {
        this.deliveryManager = deliveryManager;
    }

    public EmployeeWorkSpaceInfo getEmployeeWorkSpace() {
        return employeeWorkSpace;
    }

    public void setEmployeeWorkSpace(EmployeeWorkSpaceInfo employeeWorkSpace) {
        this.employeeWorkSpace = employeeWorkSpace;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public OnCallSchedule getOnCallSchedule() {
        return onCallSchedule;
    }

    public void setOnCallSchedule(OnCallSchedule onCallSchedule) {
        this.onCallSchedule = onCallSchedule;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Boolean getCriticalServiceSupported() {
        return criticalServiceSupported;
    }

    public void setCriticalServiceSupported(Boolean criticalServiceSupported) {
        this.criticalServiceSupported = criticalServiceSupported;
    }

    public EmployeeBasicInfo getEmployeeBackup() {
        return employeeBackup;
    }

    public void setEmployeeBackup(EmployeeBasicInfo employeeBackup) {
        this.employeeBackup = employeeBackup;
    }

    public float getTotalOnCallHours() {
        return totalOnCallHours;
    }

    public void setTotalOnCallHours(float totalOnCallHours) {
        this.totalOnCallHours = totalOnCallHours;
    }

    public BCPRole getBcpRole() {
        return bcpRole;
    }

    public void setBcpRole(BCPRole bcpRole) {
        this.bcpRole = bcpRole;
    }

    public Capped getCapped() {
        return capped;
    }

    public void setCapped(Capped capped) {
        this.capped = capped;
    }

    public float getCappedHours() {
        return cappedHours;
    }

    public void setCappedHours(float cappedHours) {
        this.cappedHours = cappedHours;
    }

    public String getProjectEmailAddress() {
        return projectEmailAddress;
    }

    public void setProjectEmailAddress(String projectEmailAddress) {
        this.projectEmailAddress = projectEmailAddress;
    }
}
