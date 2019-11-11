package com.arosyadi.promah.login;

public class UserModel {
    public String username, email;
    public Object unit;

    public UserModel(String username, Object unit, String email) {
        this.username = username;
        this.unit = unit;
        this.email = email;
    }
}
