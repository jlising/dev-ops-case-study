package com.ibm.ph.edm.common.dto;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class EmployeeActionItemSelectionInfo {
    private String employee_id;
    private boolean selected;

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
