package com.ibm.ph.edm.common.entities;

import javax.persistence.*;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
@Entity
@Table(name="employee_workspace")
public class EmployeeWorkSpace extends AbstractEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_project_id")
    private EmployeeProject employeeProject;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "building_address")
    private String buildingAddress;

    @Column(name = "floor_level")
    private String floorLevel;

    public EmployeeProject getEmployeeProject() {
        return employeeProject;
    }

    public void setEmployeeProject(EmployeeProject employeeProject) {
        this.employeeProject = employeeProject;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public String getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(String floorLevel) {
        this.floorLevel = floorLevel;
    }
}
