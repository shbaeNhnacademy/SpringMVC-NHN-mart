package com.nhnacademy.project.config;

import com.nhnacademy.project.Base;
import com.nhnacademy.project.repository.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackageClasses = Base.class,
        excludeFilters = { @ComponentScan.Filter(Controller.class)})
public class RootConfig {

    @Bean
    public ManagerRepository studentRepository() {
        ManagerRepository managerRepository = new ManagerRepositoryImpl();
        managerRepository.addManager("admin", "12345", "박매니저");

        return managerRepository;
    }

    @Bean
    public UserRepository userRepository() {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.addUser("customerKim", "1234","김고객");

        return userRepository;
    }

    @Bean
    public SessionRepository sessionRepository() {
        return new SessionRepositoryImpl();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("message");

        return messageSource;
    }

}
