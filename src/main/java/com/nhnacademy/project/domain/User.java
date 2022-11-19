package com.nhnacademy.project.domain;

import lombok.Value;

@Value
public class User {
    private final String id;

    private final String password;

    private final String name;
    private final UserGrade grade;

    public static User create(String id, String password, String name, UserGrade grade) {
        return new User(id, password, name, grade);
    }

    private User(String id, String password, String name, UserGrade grade) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.grade = grade;
    }
}
