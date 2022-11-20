package com.nhnacademy.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Answer {
    private User admin;
    @Size(min = 1, max = 40000)
    private String answerContent;
}
