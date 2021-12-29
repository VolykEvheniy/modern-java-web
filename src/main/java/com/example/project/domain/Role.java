package com.example.project.domain;

import java.util.UUID;

public class Role extends Entity {
    private String name;

    public Role() {
    }

    public Role(UUID id, boolean deleted, String name) {
        super(id, deleted);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
