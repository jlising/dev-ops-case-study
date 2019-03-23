package com.ibm.ph.edm.common.dto;

import java.util.*;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class EmployeeInfo {
    private String id;
    private String lname;
    private String mname;
    private String fname;
    private String suffix;

    private String email;
    private String serial;
    private String position;
    private boolean pem;
    private boolean tl;
    private boolean dm;
    private String ibmUID;

    private String primarySkillSet;
    private String secondarySkillSet;
    private String jrss;

    private EmployeeBasicInfo peopleManager;

    private List<EmployeeContactInfo> employeeContacts = new ArrayList<EmployeeContactInfo>();

    private List<EmployeeProjectInfo> employeeProjects = new ArrayList<EmployeeProjectInfo>();

    private Set<RoleInfo> accessRoles = new HashSet<RoleInfo>(0);
    private List<EmployeeReimbursementInfo> employeeReimbursements = new ArrayList<EmployeeReimbursementInfo>();

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

    public String getIbmUID() {
        return ibmUID;
    }

    public void setIbmUID(String ibmUID) {
        this.ibmUID = ibmUID;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public EmployeeBasicInfo getPeopleManager() {
        return peopleManager;
    }

    public void setPeopleManager(EmployeeBasicInfo peopleManager) {
        this.peopleManager = peopleManager;
    }

    public List<EmployeeContactInfo> getEmployeeContacts() {
        return employeeContacts;
    }

    public void setEmployeeContacts(List<EmployeeContactInfo> employeeContacts) {
        this.employeeContacts = employeeContacts;
    }

    public List<EmployeeProjectInfo> getEmployeeProjects() {
        return employeeProjects;
    }

    public void setEmployeeProjects(List<EmployeeProjectInfo> employeeProjects) {
        this.employeeProjects = employeeProjects;
    }

    public Set<RoleInfo> getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(Set<RoleInfo> accessRoles) {
        this.accessRoles = accessRoles;
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

    public List<EmployeeReimbursementInfo> getEmployeeReimbursements() {
        return employeeReimbursements;
    }

    public void setEmployeeReimbursements(List<EmployeeReimbursementInfo> employeeReimbursements) {
        this.employeeReimbursements = employeeReimbursements;
    }

    public boolean isDm() {
        return dm;
    }

    public void setDm(boolean dm) {
        this.dm = dm;
    }
}
