package com.example.project.controller.exception;

public class InvalidPinActivityRequestDataException extends RuntimeException {
    public InvalidPinActivityRequestDataException() {
        super("Неправильні дані для прикріплення активності");
    }
}
