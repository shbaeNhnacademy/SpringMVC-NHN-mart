package com.nhnacademy.project.repository;

import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.domain.InquiryCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InquiryRepositoryImplTest {
    InquiryRepository inquiryRepository;
    @BeforeEach
    void setUp() {
        inquiryRepository = new InquiryRepositoryImpl();
    }

    //TODO 실패 케이스 작성 필요
    @Test
    @DisplayName("register - 성공")
    void register_success() {
        Inquiry build = Inquiry.builder()
                .writerId("tester")
                .title("aa")
                .content("sfqfdscsa")
                .category(InquiryCategory.COMPLAINT)
                .writeDateTime(LocalDateTime.now())
                .build();
        long register = inquiryRepository.register(build);

        assertThat(register).isEqualTo(1L);
    }


    @Test
    @DisplayName("remove - 성공")
    void remove_success() {
        Inquiry build = Inquiry.builder()
                .writerId("tester")
                .title("aa")
                .content("sfqfdscsa")
                .category(InquiryCategory.COMPLAINT)
                .writeDateTime(LocalDateTime.now())
                .build();
        long register = inquiryRepository.register(build);

        Inquiry remove = inquiryRepository.remove(register);

        assertThat(inquiryRepository.getInquiries().size()).isZero();
        assertThat(remove.getTitle()).isEqualTo("aa");
    }

    @Test
    @DisplayName("getInquiry - 성공")
    void getInquiry_success() {
        Inquiry build = Inquiry.builder()
                .writerId("tester")
                .title("aa")
                .content("sfqfdscsa")
                .category(InquiryCategory.COMPLAINT)
                .writeDateTime(LocalDateTime.now())
                .build();
        long register = inquiryRepository.register(build);

        Inquiry inquiry = inquiryRepository.getInquiry(register);
        assertThat(inquiry.getTitle()).isEqualTo("aa");
    }

    @Test
    @DisplayName("getInquiries - 성공")
    void getInquiries_success() {
        Inquiry build = Inquiry.builder()
                .writerId("tester")
                .title("aa")
                .content("sfqfdscsa")
                .category(InquiryCategory.COMPLAINT)
                .writeDateTime(LocalDateTime.now())
                .build();
        for (int i = 0; i < 10; i++) {
            inquiryRepository.register(build);
        }

        assertThat(inquiryRepository.getInquiries()).hasSize(10);
    }
}
