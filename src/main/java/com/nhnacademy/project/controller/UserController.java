package com.nhnacademy.project.controller;

import com.nhnacademy.project.config.RootConfig;
import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.domain.InquiryRegisterRequest;
import com.nhnacademy.project.exception.IllegalExtensionException;
import com.nhnacademy.project.exception.ValidationFailedException;
import com.nhnacademy.project.repository.InquiryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/cs")
public class UserController {
    private final InquiryRepository inquiryRepository;

    public UserController(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @GetMapping
    public String getUserMain(HttpServletRequest request, ModelMap modelMap) {
        String userId = (String) request.getSession(false).getAttribute("login");
        List<Inquiry> inquiries = inquiryRepository.getInquiriesByUserId(userId);
        List<Inquiry> collect = inquiries.stream()
                .sorted(Comparator.comparing(Inquiry::getId).reversed())
                .collect(Collectors.toList());

        modelMap.put("id", userId);
        modelMap.put("inquiries", collect);

        return "thymeleaf/userMain";
    }


}
