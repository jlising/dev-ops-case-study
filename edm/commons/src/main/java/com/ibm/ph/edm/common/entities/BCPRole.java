package com.ibm.ph.edm.common.entities;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public enum BCPRole {
    BCP_CRITICAL (1),
    NON_BCP_CRITICAL (2),
    BACKUP_TO_BCP(3);

    private final int code;

    BCPRole(int code) {
        this.code = code;
    }
}
