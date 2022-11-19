package com.nhnacademy.project.repository;


import javax.servlet.http.HttpSession;

public interface SessionRepository {
    boolean exists(String sessionId);

    String add(String sessionId, HttpSession session);

    HttpSession remove(String sessionId);

    HttpSession getSession(String sessionId);
}
