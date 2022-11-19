package com.nhnacademy.project.controller;

import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.domain.InquiryCategory;
import com.nhnacademy.project.repository.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {
    private MockMvc mockMvc;
    private InquiryRepository inquiryRepository;

    private List<Inquiry> inquiries;
    @BeforeEach
    void setUp() {
        inquiryRepository = mock(InquiryRepository.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(inquiryRepository))
                .build();
        inquiries = new ArrayList<>();
        inquiries.add(new Inquiry("writer",
                "title",
                InquiryCategory.COMPLIMENT,
                LocalDateTime.now(), "content"));
    }

    @Test
    @DisplayName("getUserMain - 성공")
    void getUserMain_success() throws Exception {
        String userId = "writer";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/cs");
        builder.sessionAttr("login", userId);

        when(inquiryRepository.getInquiriesByUserId(any())).thenReturn(inquiries);

        mockMvc.perform(builder)
                .andExpect(view().name("thymeleaf/userMain"))
                .andExpect(model().attribute("id", userId))
                .andExpect(model().attribute("inquiries", inquiries))
                .andExpect(status().isOk());
        verify(inquiryRepository, times(1)).getInquiriesByUserId(any());
    }
}
