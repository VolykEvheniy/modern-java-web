package com.example.project.controller.exception;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException() {
        super("Ідентифікатор не правильний");
    }
}
