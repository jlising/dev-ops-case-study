package com.ibm.ph.edm.common.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Entity
@Table(name = "employee_project")
public class EmployeeProject extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name="team")
    private String teamName;

    @OneToOne
    @JoinColumn(name = "team_leader_id", nullable = true)
    private Employee teamLead;

    @Column(name="email", nullable=true)
    private String projectEmailAddress;

    @OneToOne
    @JoinColumn(name = "delivery_manager_id", nullable = true)
    private Employee deliveryManager;

    private int priority; //1 - primary, 2 - secondary, etc..

    @OneToOne(mappedBy = "employeeProject")
    @JoinColumn(name = "employee_workspace_id", nullable = true)
    private EmployeeWorkSpace employeeWorkSpace;

    private Shift shift;

    @Column(name = "on_call_schedule")
    private OnCallSchedule onCallSchedule;

    @Column(name = "total_on_call_hours")
    private float totalOnCallHours;

    @Column(name = "bcp_role", nullable=true)
    private BCPRole bcpRole;

    @Column(name = "critical_service_supported")
    private Boolean criticalServiceSupported = false;

    @OneToOne
    @JoinColumn(name = "employee_backup_id", nullable = true)
    private Employee employeeBackup;

    @Column(name = "capped", nullable=true)
    private Capped capped;

    @Column(name = "capped_hours")
    private float cappedHours;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeProject")
    private List<EmployeeReimbursement> employeeReimbursements = new ArrayList<EmployeeReimbursement>();

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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Employee getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(Employee teamLead) {
        this.teamLead = teamLead;
    }

    public Employee getDeliveryManager() {
        return deliveryManager;
    }

    public void setDeliveryManager(Employee deliveryManager) {
        this.deliveryManager = deliveryManager;
    }

    public EmployeeWorkSpace getEmployeeWorkSpace() {
        return employeeWorkSpace;
    }

    public void setEmployeeWorkSpace(EmployeeWorkSpace employeeWorkSpace) {
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

    public Employee getEmployeeBackup() {
        return employeeBackup;
    }

    public void setEmployeeBackup(Employee employeeBackup) {
        this.employeeBackup = employeeBackup;
    }

    public List<EmployeeReimbursement> getEmployeeReimbursements() {
        return employeeReimbursements;
    }

    public void setEmployeeReimbursements(List<EmployeeReimbursement> employeeReimbursements) {
        this.employeeReimbursements = employeeReimbursements;
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
