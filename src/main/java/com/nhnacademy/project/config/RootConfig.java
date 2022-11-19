package com.nhnacademy.project.config;

import com.nhnacademy.project.Base;
import com.nhnacademy.project.domain.Inquiry;
import com.nhnacademy.project.domain.InquiryCategory;
import com.nhnacademy.project.domain.UserGrade;
import com.nhnacademy.project.repository.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Configuration
@ComponentScan(basePackageClasses = Base.class,
        excludeFilters = { @ComponentScan.Filter(Controller.class)})
public class RootConfig {
    public static final String UPLOAD_DIR = "/Users/suhan/Downloads/";
    @Bean
    public ManagerRepository studentRepository() {
        ManagerRepository managerRepository = new ManagerRepositoryImpl();
        managerRepository.addManager("admin", "12345", "박매니저");

        return managerRepository;
    }

    @Bean
    public UserRepository userRepository() {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.addUser("user", "1234","김고객", UserGrade.CUSTOMER);
        userRepository.addUser("merge", "1234", "박고객", UserGrade.CUSTOMER);

        return userRepository;
    }

    @Bean
    public SessionRepository sessionRepository() {
        return new SessionRepositoryImpl();
    }

    @Bean
    public InquiryRepository inquiryRepository() {
        InquiryRepositoryImpl inquiryRepository = new InquiryRepositoryImpl();
        Inquiry test = Inquiry.builder().title("test")
                .writerId("merge")
                .content("테스트입니다.")
                .category(InquiryCategory.COMPLIMENT)
                .writeDateTime(LocalDateTime.now())
                .build();
        inquiryRepository.register(test);
        return inquiryRepository;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("message");

        return messageSource;
    }

}
