package com.ibm.ph.edm.security;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class LoginRequest {
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
