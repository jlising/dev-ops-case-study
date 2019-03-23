package com.ibm.ph.edm.common.entities;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public enum Shift {
    REGULAR (0),
    FIRST (1),
    MID(2),
    NIGHT(3),
    ROTATION(4);

    private final int code;

    Shift(int code) {
        this.code = code;
    }
}
