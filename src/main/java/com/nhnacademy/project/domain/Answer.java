package com.nhnacademy.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class Answer {
    private String adminId;
    @Size(min = 1, max = 40000)
    private String answerContent;
}
