package com.nhnacademy.project.repository;


import com.nhnacademy.project.domain.Manager;
import com.nhnacademy.project.exception.ManagerAlreadyExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ManagerRepositoryImpl implements ManagerRepository {
    private final Map<String, Manager> managerMap = new ConcurrentHashMap<>();

    @Override
    public boolean exists(String id) {
        return managerMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getManager(id))
                       .map(manager -> manager.getPassword().equals(password))
                       .orElse(false);
    }

    @Override
    public Manager addManager(String id, String password, String name) {
        if (exists(id)) {
            throw new ManagerAlreadyExistsException();
        }
        Manager manager = Manager.create(id, password, name);

        managerMap.put(id, manager);
        return manager;
    }

    @Override
    public Manager getManager(String id) {
        return exists(id) ? managerMap.get(id) : null;
    }



}
