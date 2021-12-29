package com.example.project.controller.exception;

public class InvalidSessionUserException extends RuntimeException {
    public InvalidSessionUserException() {
        super("Дані сесії не правильні");
    }
}
