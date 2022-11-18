package com.nhnacademy.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InquiryRegisterRequest {
    private String writerId;
    @Size(min = 2, max = 200)
    private String title;
    private InquiryCategory category;
    @Size(max = 40000)
    private String content;
    @Nullable
    private MultipartFile[] files;
}
