package com.example.project.controller.exception;

public class NotAllowedException extends RuntimeException {
    public NotAllowedException() {
        super("Ви не маєте прав для здійснення цієї дії");
    }
}
