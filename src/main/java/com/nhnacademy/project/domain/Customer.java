package com.nhnacademy.project.domain;

import lombok.Value;

@Value
public class Customer {
    private final String id;

    private final String password;

    private final String name;

    private static Customer create(String id, String password, String name) {
        return new Customer(id, password, name);
    }

    private Customer(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

}
