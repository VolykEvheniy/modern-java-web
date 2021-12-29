package com.example.project.domain.enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    public String name;

    RoleEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
