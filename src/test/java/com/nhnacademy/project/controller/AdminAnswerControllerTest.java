package com.nhnacademy.project.controller;

import com.nhnacademy.project.config.RootConfig;
import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.domain.InquiryCategory;
import com.nhnacademy.project.exception.InquiryNotFoundException;
import com.nhnacademy.project.repository.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminAnswerControllerTest {

    private MockMvc mockMvc;
    private InquiryRepository inquiryRepository;

    private Inquiry inquiry;

    @BeforeEach
    void setUp() {
        inquiryRepository = mock(InquiryRepository.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new InquiryController(inquiryRepository))
                .build();

        inquiry = new Inquiry("writer",
                "title",
                InquiryCategory.COMPLIMENT,
                LocalDateTime.now(), "content");
    }

    @Test
    void getInquiryForAnswer(){
    }


    @Test
    void postAnswer() {
    }
}
