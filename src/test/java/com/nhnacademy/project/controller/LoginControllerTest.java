package com.nhnacademy.project.controller;

import com.nhnacademy.project.domain.User;
import com.nhnacademy.project.domain.UserGrade;
import com.nhnacademy.project.exception.WrongLoginInfoException;
import com.nhnacademy.project.repository.SessionRepository;
import com.nhnacademy.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginControllerTest {
    private final static String LOGIN_FORM_PATH = "thymeleaf/loginForm";
    private MockMvc mockMvc;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        sessionRepository = mock(SessionRepository.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new LoginController(userRepository, sessionRepository))
                .build();
    }


    @Test
    @DisplayName("login - 실패 : session 없음")
    void login_noSession_fail() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");

        mockMvc.perform(builder)
                .andExpect(view().name(LOGIN_FORM_PATH));
        verify(userRepository, never()).exists(any());
    }

    @Test
    @DisplayName("login - 실패 : session 속성값(login) 없음")
    void login_noSessionAttribute_fail() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
        builder.sessionAttr("wrongAttr", "test");

        mockMvc.perform(builder)
                .andExpect(view().name(LOGIN_FORM_PATH));
        verify(userRepository, never()).exists(any());
    }

    @Test
    @DisplayName("login - 실패 : 등록된 유저가 아님")
    void login_nonRegisteredUser_fail() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
        builder.sessionAttr("login", "test");

        when(userRepository.exists(any())).thenReturn(false);
        mockMvc.perform(builder)
                .andExpect(view().name(LOGIN_FORM_PATH));
        verify(userRepository, times(1)).exists(any());
    }

    @Test
    @DisplayName("login - 성공 : 고객 등급 유저 ")
    void login_customer_success() throws Exception {
        String login = "test";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
        builder.sessionAttr("login", login);

        User user = User.create(login, "1234", "test", UserGrade.CUSTOMER);

        when(userRepository.exists(login)).thenReturn(true);
        when(userRepository.getUser(login)).thenReturn(user);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(forwardedUrl("/cs"))
                .andExpect(model().attribute("id",login))
                .andExpect(status().isOk());
        verify(userRepository, times(1)).exists(login);
    }

    @Test
    @DisplayName("login - 성공 : cs 매니저 등급 유저 ")
    void login_csManager_success() throws Exception {
        String login = "test";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
        builder.sessionAttr("login", login);

        User csManger = User.create(login, "1234", "test", UserGrade.CS_MANAGER);

        when(userRepository.exists(login)).thenReturn(true);
        when(userRepository.getUser(login)).thenReturn(csManger);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(forwardedUrl("/cs/admin"))
                .andExpect(model().attribute("id",login))
                .andExpect(status().isOk());
        verify(userRepository, times(1)).exists(login);
    }

    @Test
    @DisplayName("doLogin - 실패 : 아이디, 비밀번호가 맞지않음")
    void doLogin_notMatchedLoginInfo_fail() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/");
        builder.param("id", "test").param("pwd", "1234");

        when(userRepository.matches(anyString(), anyString())).thenReturn(false);

        Throwable th = catchThrowable(() -> mockMvc.perform(builder).andDo(print()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(WrongLoginInfoException.class);
    }

    @Test
    @DisplayName("doLogin - 성공 : 고객 등급 유저")
    void doLogin_customer_success() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/");
        builder.param("id", "test").param("pwd", "1234");

        User user = User.create("test", "1234", "test", UserGrade.CUSTOMER);

        when(userRepository.matches(any(), any())).thenReturn(true);
        when(userRepository.getUser(any())).thenReturn(user);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(cookie().exists("SESSION"))
                .andExpect(redirectedUrl("/cs"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("doLogin - 성공 : CS 매니저 등급 유저")
    void doLogin_csManager_success() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/");
        builder.param("id", "test").param("pwd", "1234");

        User csManager = User.create("test", "1234", "test", UserGrade.CS_MANAGER);

        when(userRepository.matches(any(), any())).thenReturn(true);
        when(userRepository.getUser(any())).thenReturn(csManager);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(cookie().exists("SESSION"))
                .andExpect(redirectedUrl("/cs/admin"))
                .andExpect(status().is3xxRedirection());
    }
}
