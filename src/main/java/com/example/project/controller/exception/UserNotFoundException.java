package com.example.project.controller.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Користувач не знайдений");
    }
}
