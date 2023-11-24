package com.project.showbooking.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException() {
        super("Invalid user input!! Please try again\n");
    }
}
