package com.nhnacademy.project.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
public class Inquiry {
    @Setter
    private long id;
    private final String title;
    private final InquiryCategory category;
    private final LocalDateTime writeDateTime;
    private final String content;
    @Setter
    private boolean isAnswered;

    @Setter
    private String answerContent;
    @Setter
    private Manager csManager;
    @Setter
    private LocalDateTime answeredDateTime;

    @Builder
    public Inquiry(String title, InquiryCategory category, LocalDateTime writeDateTime, String content) {
        this.title = title;
        this.category = category;
        this.writeDateTime = writeDateTime;
        this.content = content;
    }
}
