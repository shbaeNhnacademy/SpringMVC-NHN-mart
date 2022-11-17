package com.nhnacademy.project.domain;

import lombok.Value;

@Value
public class Manager {
    private final String id;

    private final String password;

    private final String name;

    public static Manager create(String id, String password, String name) {
        return new Manager(id, password, name);
    }

    private Manager(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

}
