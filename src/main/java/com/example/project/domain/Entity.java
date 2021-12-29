package com.example.project.domain;

import java.util.UUID;

public class Entity {
    private UUID id;
    private boolean deleted;

    public Entity() {
    }

    public Entity(UUID id, boolean deleted) {
        this.id = id;
        this.deleted = deleted;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
