package com.nhnacademy.project.controller;

import com.nhnacademy.project.config.RootConfig;
import com.nhnacademy.project.domain.Answer;
import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.domain.User;
import com.nhnacademy.project.exception.ValidationFailedException;
import com.nhnacademy.project.repository.InquiryRepository;
import com.nhnacademy.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;


@Slf4j
@Controller
@RequestMapping("/cs/admin/answer")
public class AdminAnswerController {
    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    public AdminAnswerController(InquiryRepository inquiryRepository, UserRepository userRepository) {
        this.inquiryRepository = inquiryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{inquiryId}")
    public String getInquiryForAnswer(@PathVariable("inquiryId") long id,
                                   @RequestParam("admin") String adminId,
                                   ModelMap modelMap) {
        Inquiry inquiry = inquiryRepository.getInquiry(id);

        modelMap.put("inquiry", inquiry);
        modelMap.put("path", RootConfig.UPLOAD_DIR);
        modelMap.put("id", adminId);

        return "thymeleaf/adminAnswer";
    }

    @PostMapping("/{inquiryId}")
    public String postAnswer(@PathVariable("inquiryId") long id,
                             @Valid @ModelAttribute Answer answer,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        Inquiry inquiry = inquiryRepository.getInquiry(id);

        User user = userRepository.getUser(answer.getAdminId());

        inquiry.setAdmin(user);
        inquiry.setAnswerContent(answer.getAnswerContent());
        inquiry.setAnsweredDateTime(LocalDateTime.now());
        inquiry.setAnswered(true);

        return "redirect:/cs/admin";

    }

    @ExceptionHandler(ValidationFailedException.class)
    public String handleValidationFailedException(HttpServletRequest request,
                                                  Model model) {

        String requestURI = request.getRequestURI();
        String id = requestURI.substring(requestURI.lastIndexOf('/') + 1);

        Inquiry inquiry = inquiryRepository.getInquiry(Long.parseLong(id));
        String adminId = (String) request.getSession(false).getAttribute("login");

        model.addAttribute("id", adminId);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("path", RootConfig.UPLOAD_DIR);

        return "thymeleaf/adminAnswer";
    }
}
