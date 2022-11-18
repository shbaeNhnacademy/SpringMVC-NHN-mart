package com.nhnacademy.project.controller;

import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.repository.InquiryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        List<Inquiry> inquiries = inquiryRepository.getInquiries();

        modelMap.put("id", request.getSession(false).getAttribute("login"));
        modelMap.put("inquiries", inquiries);

        return "thymeleaf/userMain";
    }
}
