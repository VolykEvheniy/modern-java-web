package com.example.project.controller.exception;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException() {
        super("Активність не знайдено");
    }
}
