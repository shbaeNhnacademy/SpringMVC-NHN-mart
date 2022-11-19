package com.nhnacademy.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class InquiryRegisterRequest {
    public static final int TITLE_MIN_SIZE = 2;
    public static final int TITLE_MAX_SIZE = 200;
    public static final int CONTENT_MAX_SIZE = 40000;

    private String writerId;
    @Size(min = TITLE_MIN_SIZE, max = TITLE_MAX_SIZE)
    private String title;
    private InquiryCategory category;
    @Size(max = CONTENT_MAX_SIZE)
    private String content;
    @Nullable
    private MultipartFile[] files;
}
