package com.nhnacademy.project.repository;

import com.nhnacademy.project.domain.User;
import com.nhnacademy.project.domain.UserGrade;

public interface UserRepository {
    boolean exists(String id);
    boolean matches(String id, String password);

    User add(String id, String password, String name, UserGrade grade);

    User getUser(String id);

}
