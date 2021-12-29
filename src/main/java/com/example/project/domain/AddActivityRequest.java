package com.example.project.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class AddActivityRequest extends Entity {
    private User user;
    private boolean accepted;
    private LocalDateTime reviewedAt;
    private String name;
    private Category category;
    private String description;
    private String note;

    public AddActivityRequest() {

    }

    public AddActivityRequest(UUID id, boolean deleted, boolean accepted,
                              LocalDateTime reviewedAt, User user, String name,
                              Category category, String description, String note) {
        super(id, deleted);
        this.user = user;
        this.accepted = accepted;
        this.reviewedAt = reviewedAt;
        this.name = name;
        this.category = category;
        this.description = description;
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
