package com.example.project.controller.exception;

public class InvalidSetTimeSpentRequestDataException extends RuntimeException {
    public InvalidSetTimeSpentRequestDataException() {
        super("Неправильні дані для встановлення часу");
    }
}
