package com.example.project.controller.exception;

public class InvalidCreateAddActivityRequestDataException extends RuntimeException {
    public InvalidCreateAddActivityRequestDataException() {
        super("Неправильні дані для створення запиту на додавання активності");
    }
}
