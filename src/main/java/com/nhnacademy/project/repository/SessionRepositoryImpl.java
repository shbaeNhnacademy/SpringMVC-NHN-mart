package com.nhnacademy.project.repository;

import com.nhnacademy.project.exception.SessionNotFoundException;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionRepositoryImpl implements SessionRepository{

    private final Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public boolean exists(String sessionId) {
        return sessionMap.containsKey(sessionId);
    }


    @Override
    public String addSession(String sessionId, HttpSession session) {
        if (exists(sessionId)) {
            throw new IllegalStateException("Duplicate Session");
        }
        sessionMap.put(sessionId, session);
        return sessionId;
    }

    @Override
    public HttpSession getManager(String sessionId) {
        if (!exists(sessionId)) {
            throw new SessionNotFoundException();
        }
        return sessionMap.get(sessionId);
    }
}
