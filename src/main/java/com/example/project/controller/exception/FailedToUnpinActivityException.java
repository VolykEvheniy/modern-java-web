package com.example.project.controller.exception;

public class FailedToUnpinActivityException extends RuntimeException {
    public FailedToUnpinActivityException() {
        super("Помилка при відкріпленні користувача від активності");
    }
}
