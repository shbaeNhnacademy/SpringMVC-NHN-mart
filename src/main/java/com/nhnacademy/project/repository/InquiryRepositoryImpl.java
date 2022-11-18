package com.nhnacademy.project.repository;

import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.exception.InquiryNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InquiryRepositoryImpl implements InquiryRepository{
    private final Map<Long, Inquiry> inquiryMap = new ConcurrentHashMap<>();

    @Override
    public long register(Inquiry inquiry) {
        Long id = inquiryMap.keySet().stream()
                .max(Long::compareTo)
                .map(l -> l + 1)
                .orElse(1L);
        inquiry.setId(id);

        inquiryMap.put(id, inquiry);

        return id;
    }

    @Override
    public Inquiry remove(long id) {
        if (!exist(id)) {
            throw new InquiryNotFoundException();
        }
        return inquiryMap.remove(id);
    }

    @Override
    public Inquiry getInquiry(long id) {
        if (!exist(id)) {
            throw new InquiryNotFoundException();
        }
        return inquiryMap.get(id);
    }

    @Override
    public List<Inquiry> getInquiries() {
        return new ArrayList<>(inquiryMap.values());
    }

    private boolean exist(long id){
        return inquiryMap.containsKey(id);
    }
}
