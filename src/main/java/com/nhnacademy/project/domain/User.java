package com.nhnacademy.project.domain;

import lombok.Value;

@Value
public class User {
    private final String id;

    private final String password;

    private final String name;

    public static User create(String id, String password, String name) {

        return new User(id, password, name);
    }

    private User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    private static final String MASK = "*****";

    public static User constructPasswordMaskedUser(User user) {
        return User.create(user.getId(), MASK, user.getName());
    }

}
