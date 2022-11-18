package com.nhnacademy.project.domain;

public enum InquiryCategory {
    COMPLAINT("불만 접수"),
    SUGGESTION("제안"),
    REFUND_EXCHANGE("환불/교환"),
    COMPLIMENT("칭찬해요"),
    ETC("기타문의")
    ;

    private final String name;

    InquiryCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
