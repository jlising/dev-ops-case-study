package com.ibm.ph.edm.common.entities;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public enum OnCallSchedule {
    NO(0),
    WEEKLY  (1),
    MONTHLY(2)
    ; // semicolon needed when fields / methods follow


    private final int code;

    OnCallSchedule(int code) {
        this.code = code;
    }
}
