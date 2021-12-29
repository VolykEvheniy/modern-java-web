package com.example.project.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public class User2Activity extends Entity {
    private User user;
    private Activity activity;
    private BigInteger timeSpent;
    private boolean completed;
    private LocalDateTime assigned_at;

    public User2Activity() {
    }

    public User2Activity(
            UUID id,
            boolean deleted,
            User user,
            Activity activity,
            BigInteger timeSpent,
            boolean completed,
            LocalDateTime assigned_at
    ) {
        super(id, deleted);
        this.user = user;
        this.activity = activity;
        this.timeSpent = timeSpent;
        this.completed = completed;
        this.assigned_at = assigned_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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

    public LocalDateTime getAssigned_at() {
        return assigned_at;
    }

    public void setAssigned_at(LocalDateTime assigned_at) {
        this.assigned_at = assigned_at;
    }
}
