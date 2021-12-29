package com.example.project.controller.exception;

public class InvalidShowActivityManagementRequestException extends RuntimeException {
    public InvalidShowActivityManagementRequestException() {
        super("Неправильні дані для показу керування активністю");
    }
}
