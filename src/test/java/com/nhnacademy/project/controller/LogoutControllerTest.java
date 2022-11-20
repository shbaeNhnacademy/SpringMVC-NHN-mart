package com.nhnacademy.project.controller;

import com.nhnacademy.project.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LogoutControllerTest {
    private final static String RE_LOGIN_PATH = "redirect:/";
    private MockMvc mockMvc;
    private SessionRepository sessionRepository;
    @BeforeEach
    void setUp() {
        sessionRepository = mock(SessionRepository.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new LogoutController(sessionRepository))
                .build();
    }

    @Test
    @DisplayName("logout - 실패 : session 없음")
    void logout_noSession_fail() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/logout");

        mockMvc.perform(builder)
                .andExpect(view().name(RE_LOGIN_PATH))
                .andExpect(status().is3xxRedirection());
        verify(sessionRepository, never()).exists(any());
    }

    @Test
    @DisplayName("logout - 실패 : session 저장소에 해당 session 없음")
    void logout_notExistSession_fail() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/logout");
        builder.sessionAttr("login", "test");

        when(sessionRepository.exists(any())).thenReturn(false);

        mockMvc.perform(builder)
                .andExpect(view().name(RE_LOGIN_PATH))
                .andExpect(status().is3xxRedirection());
        verify(sessionRepository, times(1)).exists(any());
        verify(sessionRepository, never()).remove(any());
    }

    @Test
    @DisplayName("logout - 성공")
    void logout_success() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/logout");
        builder.sessionAttr("login", "test");

        when(sessionRepository.exists(any())).thenReturn(true);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(view().name(RE_LOGIN_PATH))
                .andExpect(cookie().value("SESSION", ""))
                .andExpect(status().is3xxRedirection());
        verify(sessionRepository, times(1)).exists(any());
        verify(sessionRepository, times(1)).remove(any());
    }
}
