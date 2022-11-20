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
    @DisplayName("getInquiryForAnswer - 성공")
    void getInquiryForAnswer_success() throws Exception {
        String adminId = "admin";
        long id = 0L;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cs/admin/answer/" + id);
        builder.queryParam("admin", adminId);

        when(inquiryRepository.getInquiry(id)).thenReturn(inquiry);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(view().name("thymeleaf/adminAnswer"))
                .andExpect(model().attribute("id", adminId))
                .andExpect(model().attribute("path", RootConfig.UPLOAD_DIR))
                .andExpect(model().attribute("inquiry", inquiry))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("getInquiryForAnswer - 실패 : 해당 id의 문의가 없음")
    void getInquiryForAnswer_notfoundInquiry_fail() throws Exception {
        String adminId = "admin";
        long id = 1L;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cs/admin/answer/" + id);
        builder.queryParam("admin", adminId);

        when(inquiryRepository.getInquiry(id)).thenReturn(inquiry);
        when(inquiryRepository.getInquiry(id)).thenThrow(InquiryNotFoundException.class);

        Throwable th = catchThrowable(() -> mockMvc.perform(builder));
        assertThat(th)
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(InquiryNotFoundException.class);

    }

    @Test
    void postAnswer() {
    }
}
