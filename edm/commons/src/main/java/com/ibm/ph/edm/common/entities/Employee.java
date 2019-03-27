package com.ibm.ph.edm.common.entities;

import javax.persistence.*;
import java.util.*;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Entity
@Table(name="employee")
public class Employee extends AbstractEntity {

    private String lname;
    private String mname;
    private String fname;
    private String suffix;

    private String email;
    private String serial;

    private String position;

    @Column(name = "is_people_manager")
    private boolean pem = false;

    @Column(name = "is_delivery_manager")
    private boolean dm = false;

    @Column(name = "is_team_lead")
    private boolean tl = false;

    @OneToOne
    @JoinColumn(name = "people_manager_id", nullable = true)
    private Employee peopleManager;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private final List<EmployeeContact> employeeContacts = new ArrayList<EmployeeContact>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private final List<EmployeeProject> employeeProjects = new ArrayList<EmployeeProject>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private Set<EmployeeRole> employeeRoles = new HashSet<EmployeeRole>(0);

    @Transient
    private Set<Role> accessRoles = new HashSet<Role>(0);

    private boolean enabled = true;

    @Column(name = "ibm_uid")
    private String ibmUID;

    @Column(name="primary_skillset")
    private String primarySkillSet;

    @Column(name="secondary_skillset")
    private String secondarySkillSet;
    private String jrss;

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

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPosition() {
        return position;
    }

    public boolean isPem() {
        return pem;
    }

    public void setPem(boolean pem) {
        this.pem = pem;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Employee getPeopleManager() {
        return peopleManager;
    }

    public void setPeopleManager(Employee peopleManager) {
        this.peopleManager = peopleManager;
    }

    public List<EmployeeContact> getEmployeeContacts() {
        return employeeContacts;
    }

    public List<EmployeeProject> getEmployeeProjects() {
        return employeeProjects;
    }

    public Set<EmployeeRole> getEmployeeRoles() {
        return employeeRoles;
    }

    public void setEmployeeRoles(Set<EmployeeRole> employeeRoles) {
        this.employeeRoles = employeeRoles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getIbmUID() {
        return ibmUID;
    }

    public void setIbmUID(String ibmUID) {
        this.ibmUID = ibmUID;
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

    public Set<Role> getAccessRoles() {
        Set<Role> roles = new HashSet<Role>(0);

        Iterator<EmployeeRole> it = this.getEmployeeRoles().iterator();

        while(it.hasNext()){
            roles.add(it.next().getRole());
        }

        return roles;
    }

    public void setAccessRoles(Set<Role> accessRoles) {
        this.accessRoles = accessRoles;
    }

    public String getPrimarySkillSet() {
        return primarySkillSet;
    }

    public void setPrimarySkillSet(String primarySkillSet) {
        this.primarySkillSet = primarySkillSet;
    }

    public String getSecondarySkillSet() {
        return secondarySkillSet;
    }

    public void setSecondarySkillSet(String secondarySkillSet) {
        this.secondarySkillSet = secondarySkillSet;
    }

    public String getJrss() {
        return jrss;
    }

    public void setJrss(String jrss) {
        this.jrss = jrss;
    }

    public boolean isTl() {
        return tl;
    }

    public void setTl(boolean tl) {
        this.tl = tl;
    }

    public boolean isDm() {
        return dm;
    }

    public void setDm(boolean dm) {
        this.dm = dm;
    }
}
