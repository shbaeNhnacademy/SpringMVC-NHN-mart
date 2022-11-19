package com.nhnacademy.project.controller;

import com.nhnacademy.project.repository.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InquiryControllerTest {
    private MockMvc mockMvc;
    private InquiryRepository inquiryRepository;
    @BeforeEach
    void setUp() {
        inquiryRepository = mock(InquiryRepository.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new InquiryController(inquiryRepository))
                .build();
    }

    //TODO 실패 상황을 억지로라도 만들어내야하나?
    @Test
    void getInquiryForm_success() throws Exception {
        String userId = "writer";
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cs/inquiry");
        builder.sessionAttr("login", userId);

        mockMvc.perform(builder)
                .andExpect(view().name("thymeleaf/inquiryForm"))
                .andExpect(model().attribute("id", userId))
                .andExpect(status().isOk());
    }

    @Test
    void postInquiry() {
    }

    @Test
    void getDetailInquiry() {
    }

    @Test
    void getImagesPath() {
    }

    @Test
    void getImageByFilename() {
    }
}
