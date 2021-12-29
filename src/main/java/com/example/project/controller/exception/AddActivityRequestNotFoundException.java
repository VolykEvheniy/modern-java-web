package com.example.project.controller.exception;

public class AddActivityRequestNotFoundException extends RuntimeException {
    public AddActivityRequestNotFoundException() {
        super("Запит на додавання активності не знайдено");
    }
}
