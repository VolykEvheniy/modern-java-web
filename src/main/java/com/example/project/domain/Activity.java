package com.example.project.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Activity extends Entity {
    private String name;
    private Category category;
    private String description;
    private LocalDateTime createdAt;

    public Activity() {
    }

    public Activity(UUID id, boolean deleted, String name, Category category, String description, LocalDateTime createdAt) {
        super(id, deleted);
        this.name = name;
        this.category = category;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
