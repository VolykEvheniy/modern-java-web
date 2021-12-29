package com.example.project.controller.exception;

public class FailedToDeleteActivityException extends RuntimeException {
    public FailedToDeleteActivityException() {
        super("Помилка при видаленні активності");
    }
}
