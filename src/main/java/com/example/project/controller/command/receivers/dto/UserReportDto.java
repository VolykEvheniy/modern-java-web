package com.example.project.controller.command.receivers.dto;

import java.math.BigInteger;
import java.util.UUID;

public class UserReportDto {
    private UUID id;
    private String login;
    private Integer activitiesAmount;
    private BigInteger totalTimeSpent;

    public UserReportDto(UUID id, String login, Integer activitiesAmount, BigInteger totalTimeSpent) {
        this.id = id;
        this.login = login;
        this.activitiesAmount = activitiesAmount;
        this.totalTimeSpent = totalTimeSpent;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getActivitiesAmount() {
        return activitiesAmount;
    }

    public void setActivitiesAmount(Integer activitiesAmount) {
        this.activitiesAmount = activitiesAmount;
    }

    public BigInteger getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(BigInteger totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }
}
