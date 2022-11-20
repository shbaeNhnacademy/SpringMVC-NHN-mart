package com.nhnacademy.project.controller;

import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.repository.InquiryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
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

    @GetMapping("/admin")
    public String getAdminMain(HttpServletRequest request, ModelMap modelMap) {
        String adminId = (String) request.getSession(false).getAttribute("login");

        List<Inquiry> inquiries = inquiryRepository.getInquiries();

        List<Inquiry> collect = inquiries.stream()
                .filter(inquiry -> !inquiry.isAnswered())
                .collect(Collectors.toList());

        modelMap.put("id", adminId);
        modelMap.put("inquiries", collect);

        return "thymeleaf/adminMain";
    }
}
