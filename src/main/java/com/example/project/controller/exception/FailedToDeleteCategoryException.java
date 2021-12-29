package com.example.project.controller.exception;

public class FailedToDeleteCategoryException extends RuntimeException {
    public FailedToDeleteCategoryException() {
        super("Помилка при видаленні категорії");
    }
}
