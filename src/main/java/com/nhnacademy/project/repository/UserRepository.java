package com.nhnacademy.project.repository;

import com.nhnacademy.project.domain.User;

public interface UserRepository {
    boolean exists(String id);
    boolean matches(String id, String password);

    User addUser(String id, String password, String name);

    User getUser(String id);

}
