package com.example.project.domain;

import java.util.UUID;

public class User extends Entity {
    private String login;
    private String salt;
    private String hashedPassword;
    private Role role;

    public User() {
    }

    public User(UUID id, boolean deleted, String login, String salt, String hashedPassword, Role role) {
        super(id, deleted);
        this.login = login;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
