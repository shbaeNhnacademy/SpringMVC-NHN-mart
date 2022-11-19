package com.nhnacademy.project.repository;

import com.nhnacademy.project.domain.User;
import com.nhnacademy.project.domain.UserGrade;
import com.nhnacademy.project.exception.UserAlreadyExistsException;
import com.nhnacademy.project.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private static final String TEST_ID = "test";
    UserRepository userRepository;
    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
        userRepository.add("test", "1234", "Tester", UserGrade.CUSTOMER);
    }

    @Test
    @DisplayName("exists - 성공")
    void exists_success() {
        boolean exists = userRepository.exists(TEST_ID);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("exists - 실패 : 없는 id ")
    void exists_wrongId_fail() {
        boolean exists = userRepository.exists(TEST_ID + "1");
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("matches - 성공")
    void matches_success() {
        boolean matches = userRepository.matches(TEST_ID, "1234");
        assertThat(matches).isTrue();

    }

    @Test
    @DisplayName("matches - 실패 : 틀린 비밀번호")
    void matches_wrongPassword_fail() {
        boolean matches = userRepository.matches(TEST_ID, "wrong-pwd");
        assertThat(matches).isFalse();

    }

    @Test
    @DisplayName("add - 성공")
    void add_success() {
        String name = "test2";
        User test2 = userRepository.add(TEST_ID + "1", "1234", name, UserGrade.CUSTOMER);
        assertThat(test2.getName()).isEqualTo(name);
        assertThat(test2.getGrade()).isEqualTo(UserGrade.CUSTOMER);
    }

    @Test
    @DisplayName("add - 실패 : 존재하는 id")
    void add_alreadyExistId_fail() {
        assertThatThrownBy(() -> userRepository.add(TEST_ID, "1234", "test2", UserGrade.CUSTOMER))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    @DisplayName("getUser - 성공")
    void getUser_success() {
        User user = userRepository.getUser(TEST_ID);

        assertThat(user.getId()).isEqualTo(TEST_ID);
    }

    @Test
    @DisplayName("getUser - 실패 : 해당 id의 사용자 없음")
    void getUser_notFoundUser_fail() {
        assertThatThrownBy(() -> userRepository.getUser(TEST_ID + "1"))
                .isInstanceOf(UserNotFoundException.class);
    }
}
