package com.mycompany.plantcaresystem.models;

public class User extends BaseEntity {

    private final String username;
    private final String role;

    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getInfo() {
        return "User: " + username + " (role=" + role + ")";
    }

    @Override
    public String toString() {
        return username + " [" + role + "]";
    }
}
