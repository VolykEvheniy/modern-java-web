package com.example.project.controller.exception;

public class InvalidDeleteActivityRequestDataException extends RuntimeException {
    public InvalidDeleteActivityRequestDataException() {
        super("Неправильні дані для видалення активності");
    }
}
