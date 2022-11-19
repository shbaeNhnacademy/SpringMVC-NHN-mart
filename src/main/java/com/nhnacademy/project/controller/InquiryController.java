package com.nhnacademy.project.controller;

import com.nhnacademy.project.config.RootConfig;
import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.domain.InquiryRegisterRequest;
import com.nhnacademy.project.exception.IllegalExtensionException;
import com.nhnacademy.project.exception.ValidationFailedException;
import com.nhnacademy.project.repository.InquiryRepository;
import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/cs/inquiry")
public class InquiryController {

    private final InquiryRepository inquiryRepository;

    public InquiryController(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @GetMapping
    public String getInquiryForm(HttpServletRequest request, ModelMap modelMap) {
        String userId = (String) request.getSession(false).getAttribute("login");
        modelMap.put("id", userId);

        return "thymeleaf/inquiryForm";
    }

    @PostMapping
    public String postInquiry(@Valid @ModelAttribute InquiryRegisterRequest inquiryRegisterRequest,
                                BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        Inquiry inquiry = Inquiry.builder().writerId(inquiryRegisterRequest.getWriterId())
                .title(inquiryRegisterRequest.getTitle())
                .content(inquiryRegisterRequest.getContent())
                .category(inquiryRegisterRequest.getCategory())
                .writeDateTime(LocalDateTime.now())
                .build();

        MultipartFile[] files = inquiryRegisterRequest.getFiles();
        if (!Objects.isNull(files) && files[0].getSize() != 0) {
            for (MultipartFile file : files) {
                file.transferTo(Paths.get(RootConfig.UPLOAD_DIR + file.getOriginalFilename()));
            }
            inquiry.setFiles(files);
        }

        long register = inquiryRepository.register(inquiry);

        return "redirect:/cs/inquiry/" + register;
    }


    @GetMapping("/{inquiryId}")
    public String getDetailInquiry(@PathVariable("inquiryId") long id,
                                   ModelMap modelMap) {
        Inquiry inquiry = inquiryRepository.getInquiry(id);

        modelMap.put("inquiry", inquiry);
        modelMap.put("path", RootConfig.UPLOAD_DIR);

        return "thymeleaf/inquiryView";
    }

    @GetMapping("/{inquiryId}/{filename}/view")
    public String getImagesPath(@PathVariable("inquiryId") long id,
                                @PathVariable("filename") String filename,
                                Model model) {
        model.addAttribute("inquiryId", id);
        model.addAttribute("filename", filename);
        return "thymeleaf/attachedImageView";
    }

    @ResponseBody
    @GetMapping("/{inquiryId}/{filename}")
    public Resource getImageByFilename(@PathVariable("filename") String filename) throws MalformedURLException {
        Path path = Paths.get(RootConfig.UPLOAD_DIR + filename);
        return new UrlResource("file:" + path.toAbsolutePath());
    }
}
