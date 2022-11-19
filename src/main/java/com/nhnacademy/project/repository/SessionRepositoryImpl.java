package com.nhnacademy.project.repository;

import com.nhnacademy.project.exception.SessionNotFoundException;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionRepositoryImpl implements SessionRepository{
    //TODO 세션을 굳이 들고다닐 필요가 있나? Map<세션id,userId> 면 되지않을까
    private final Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public boolean exists(String sessionId) {
        return sessionMap.containsKey(sessionId);
    }


    @Override
    public String add(String sessionId, HttpSession session) {
        if (exists(sessionId)) {
            throw new IllegalStateException("Duplicate Session");
        }
        sessionMap.put(sessionId, session);
        return sessionId;
    }

    @Override
    public HttpSession remove(String sessionId) {
        if (!exists(sessionId)) {
            throw new SessionNotFoundException();
        }
        return sessionMap.remove(sessionId);
    }

    @Override
    public HttpSession getSession(String sessionId) {
        if (!exists(sessionId)) {
            throw new SessionNotFoundException();
        }
        return sessionMap.get(sessionId);
    }
}
