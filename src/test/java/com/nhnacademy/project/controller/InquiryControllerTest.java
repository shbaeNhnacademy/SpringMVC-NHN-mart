package com.nhnacademy.project.controller;

import com.nhnacademy.project.config.RootConfig;
import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.domain.InquiryCategory;
import com.nhnacademy.project.repository.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InquiryControllerTest {
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
    @DisplayName("getInquiryForm - 성공")
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
    @DisplayName("postInquiry - 실패 : invalid title")
    void postInquiry_invalidValue_fail() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/cs/inquiry")
                        .param("title", "")
                        .param("content", "1");

        when(inquiryRepository.register(any())).thenReturn(0L);


        Throwable th = catchThrowable(() -> mockMvc.perform(builder).andDo(print()));
        assertThat(th).isInstanceOf(NestedServletException.class);
        verify(inquiryRepository,never()).register(any());

    }

    @Test
    @DisplayName("postInquiry - 성공")
    void postInquiry_success() throws Exception{
        String writerId = "writerId";
        String title = "title";
        String content = "content";
        String files = "files";

        MockMultipartFile file = new MockMultipartFile(
                files,
                "hello.png",
                MediaType.IMAGE_PNG_VALUE,
                files.getBytes()
        );

        when(inquiryRepository.register(any())).thenReturn(0L);

        MockHttpServletRequestBuilder builder = multipart("/cs/inquiry")
                .file(file)
                .param("writerId",writerId)
                .param("title", title)
                .param("content", content)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .characterEncoding(StandardCharsets.UTF_8);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(redirectedUrl("/cs/inquiry/0"))
                .andExpect(status().is3xxRedirection());
        verify(inquiryRepository,times(1)).register(any());
    }

    @Test
    @DisplayName("getDetailInquiry - 성공")
    void getDetailInquiry_success() throws Exception {
        long inquiryId = 1;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cs/inquiry/" + inquiryId);

        when(inquiryRepository.getInquiry(inquiryId)).thenReturn(inquiry);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(view().name("thymeleaf/inquiryView"))
                .andExpect(model().attribute("inquiry", inquiry))
                .andExpect(model().attribute("path", RootConfig.UPLOAD_DIR))
                .andExpect(status().isOk());
        verify(inquiryRepository, times(1)).getInquiry(inquiryId);
    }

    @Test
    @DisplayName("getDetailInquiry - 실패 : 존재하는 Id")
    void getDetailInquiry_existId_fail() throws Exception {
        long inquiryId = 1;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cs/inquiry/" + inquiryId);

        when(inquiryRepository.getInquiry(inquiryId)).thenReturn(inquiry);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(view().name("thymeleaf/inquiryView"))
                .andExpect(model().attribute("inquiry", inquiry))
                .andExpect(model().attribute("path", RootConfig.UPLOAD_DIR))
                .andExpect(status().isOk());
        verify(inquiryRepository, times(1)).getInquiry(inquiryId);
    }

    @Test
    @DisplayName("getImagesPath - 성공")
    void getImagesPath() throws Exception {
        long inquiryId = 1;
        String filename = "test.png";
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders
                        .get("/cs/inquiry/" + inquiryId + "/" + filename + "/view");

        mockMvc.perform(builder)
                .andExpect(view().name("thymeleaf/attachedImageView"))
                .andExpect(model().attribute("inquiryId", inquiryId))
                .andExpect(model().attribute("filename", filename))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("getImageByFilename - 성공")
    void getImageByFilename_success() throws Exception {
        long inquiryId = 1;
        String filename = "Team7_Logical_ERD.png";
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders
                        .get("/cs/inquiry/" + inquiryId + "/" + filename);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}
