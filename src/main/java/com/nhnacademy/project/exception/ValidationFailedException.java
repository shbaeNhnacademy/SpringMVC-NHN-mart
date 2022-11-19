package com.nhnacademy.project.exception;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationFailedException extends RuntimeException {
    public ValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getAllErrors()
                           .stream()
                           .map(error -> new StringBuilder()
                                   .append("Message=").append(error.getDefaultMessage()).append("\n"))
                           .collect(Collectors.joining(" | ")));
    }
}
