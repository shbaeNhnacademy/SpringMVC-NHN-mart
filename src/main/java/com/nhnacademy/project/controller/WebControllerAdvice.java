package com.nhnacademy.project.controller;

import com.nhnacademy.project.domain.InquiryRegisterRequest;
import com.nhnacademy.project.exception.UserAlreadyExistsException;
import com.nhnacademy.project.exception.ValidationFailedException;
import com.nhnacademy.project.exception.WrongLoginInfoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {

    private final String EXCEPTION = "exception";
    private final String THYMELEAF_ERROR = "thymeleaf/error";

    @ExceptionHandler(WrongLoginInfoException.class)
    public String handleWrongLoginInfoException(WrongLoginInfoException ex, Model model) {
        model.addAttribute("loginInfo", ex.getMessage());
        return "thymeleaf/loginForm";
    }

    @ExceptionHandler(ValidationFailedException.class)
    public String handleValidationFailedException(ValidationFailedException ex, HttpServletRequest request, Model model) {
        String message = ex.getMessage();
        String substring = message.substring(message.lastIndexOf('=') + 1);
        StringBuilder sb = new StringBuilder();
        if (substring.contains(String.valueOf(InquiryRegisterRequest.TITLE_MAX_SIZE))) {
            sb.append("제목의 ").append(substring);
        }
        model.addAttribute("validInfo", sb.toString());

        String userId = (String) request.getSession(false).getAttribute("login");
        model.addAttribute("id", userId);

        return "thymeleaf/inquiryForm";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException ex, Model model) {
        log.info("", ex);

        model.addAttribute(EXCEPTION, ex);
        return THYMELEAF_ERROR;
    }


    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute(EXCEPTION, ex);
        return THYMELEAF_ERROR;
    }

}
