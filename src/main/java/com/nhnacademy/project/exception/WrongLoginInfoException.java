package com.nhnacademy.project.exception;

public class WrongLoginInfoException extends RuntimeException{
    public WrongLoginInfoException() {
        super("Please enter a valid ID and password");
    }
}
