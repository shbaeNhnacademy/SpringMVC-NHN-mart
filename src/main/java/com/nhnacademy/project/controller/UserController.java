package com.nhnacademy.project.controller;

import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.repository.InquiryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String getUserMain(HttpServletRequest request, Model model) {
        model.addAttribute("id", request.getSession(false).getAttribute("login"));
        return "thymeleaf/userMain";
    }

    @GetMapping("/inquiry")
    public String getInqueryList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        List<Inquiry> inquiries = inquiryRepository.getInquiries();


        modelMap.put("id", request.getSession(false).getAttribute("login"));
        modelMap.put("inquiries", inquiries);

        return "thymeleaf/userInquiry";
    }
}
