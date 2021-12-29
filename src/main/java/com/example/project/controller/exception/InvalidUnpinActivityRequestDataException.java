package com.example.project.controller.exception;

public class InvalidUnpinActivityRequestDataException extends RuntimeException {
    public InvalidUnpinActivityRequestDataException() {
        super("Неправильні дані для відкріплення активності");
    }
}
