package com.nhnacademy.project.repository;


import com.nhnacademy.project.domain.Manager;

public interface ManagerRepository {
    boolean exists(String id);
    boolean matches(String id, String password);

    Manager addManager(String id, String password, String name);

    Manager getManager(String id);
}
