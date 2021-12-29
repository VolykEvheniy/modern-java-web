package com.example.project.controller.exception;

public class InvalidCreateActivityRequestDataException extends RuntimeException {
    public InvalidCreateActivityRequestDataException() {
        super("Неправильні дані для створення активності");
    }
}
