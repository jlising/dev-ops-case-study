package com.ibm.ph.edm.common.dto;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class EmployeeBasicInfo {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
