package com.example.project.controller.command.receivers.dto;

import java.math.BigInteger;
import java.util.UUID;

public class UserActivityDto {
    private UUID id;
    private String name;
    private String description;
    private BigInteger timeSpent;
    private boolean completed;

    public UserActivityDto(UUID id, String name, String description, BigInteger timeSpent, boolean completed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timeSpent = timeSpent;
        this.completed = completed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(BigInteger timeSpent) {
        this.timeSpent = timeSpent;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
