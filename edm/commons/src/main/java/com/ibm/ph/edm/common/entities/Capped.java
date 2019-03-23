package com.ibm.ph.edm.common.entities;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public enum Capped {
    NO (0),
    DAILY (1),
    WEEKLY(2);

    private final int code;

    Capped(int code) {
        this.code = code;
    }
}
