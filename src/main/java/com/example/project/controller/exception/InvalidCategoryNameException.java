package com.example.project.controller.exception;

public class InvalidCategoryNameException extends RuntimeException {
    public InvalidCategoryNameException() {
        super("Неправильна назва категорії");
    }
}
