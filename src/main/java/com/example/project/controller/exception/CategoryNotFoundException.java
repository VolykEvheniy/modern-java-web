package com.example.project.controller.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("Категорію не знайдено");
    }
}
