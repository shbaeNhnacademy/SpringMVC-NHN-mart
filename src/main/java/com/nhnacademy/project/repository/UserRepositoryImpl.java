package com.nhnacademy.project.repository;


import com.nhnacademy.project.domain.User;
import com.nhnacademy.project.exception.UserAlreadyExistsException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    @Override
    public boolean exists(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getUser(id))
                       .map(user -> user.getPassword().equals(password))
                       .orElse(false);
    }

    @Override
    public User addUser(String id, String password, String name) {
        if (exists(id)) {
            throw new UserAlreadyExistsException();
        }
        User user = User.create(id, password, name);

        userMap.put(id, user);
        return user;
    }

    @Override
    public User getUser(String id) {
        return exists(id) ? userMap.get(id) : null;
    }



}
