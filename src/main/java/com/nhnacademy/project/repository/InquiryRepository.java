package com.nhnacademy.project.repository;

import com.nhnacademy.project.domain.Inquiry;

import java.util.List;

public interface InquiryRepository {
    long register(Inquiry inquiry);
    Inquiry remove(long id);

    Inquiry getInquiry(long id);
    List<Inquiry> getInquiries();

    List<Inquiry> getInquiriesByUserId(String userId);

}
