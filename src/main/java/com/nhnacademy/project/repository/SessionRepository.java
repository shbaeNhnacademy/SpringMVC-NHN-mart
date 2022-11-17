package com.nhnacademy.project.repository;


import javax.servlet.http.HttpSession;

public interface SessionRepository {
    boolean exists(String sessionId);

    String addSession(String sessionId, HttpSession session);

    HttpSession getManager(String sessionId);
}
