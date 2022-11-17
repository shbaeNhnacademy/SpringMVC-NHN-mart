package com.nhnacademy.project.controller;

import com.nhnacademy.project.exception.UserAlreadyExistsException;
import com.nhnacademy.project.exception.ManagerAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {

    private final String exception = "exception";
    private final String thymeleafError = "thymeleaf/error";

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException ex, Model model) {
        log.info("", ex);

        model.addAttribute(exception, ex);
        return thymeleafError;
    }

    @ExceptionHandler(ManagerAlreadyExistsException.class)
    public String handleManagerAlreadyExistsException(ManagerAlreadyExistsException ex, Model model) {
        log.info("", ex);

        model.addAttribute(exception, ex);
        return thymeleafError;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute(exception, ex);
        return thymeleafError;
    }

}
