package com.nhnacademy.project.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@ToString
public class Inquiry {
    @Setter
    private long id;
    private final String writerId;
    private final String title;
    private final InquiryCategory category;
    private final LocalDateTime writeDateTime;
    private final String content;

    @Setter
    private MultipartFile[] files;
    @Setter
    private boolean isAnswered;

    @Setter
    private String answerContent;
    @Setter
    private User admin;
    @Setter
    private LocalDateTime answeredDateTime;

    @Builder
    public Inquiry(String writerId, String title, InquiryCategory category, LocalDateTime writeDateTime, String content) {
        this.writerId = writerId;
        this.title = title;
        this.category = category;
        this.writeDateTime = writeDateTime;
        this.content = content;
    }
}
