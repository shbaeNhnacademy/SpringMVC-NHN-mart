package com.nhnacademy.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (!Objects.isNull(session)) {
            session.setAttribute("login", "");
            session.invalidate();

            Cookie cookie = new Cookie("SESSION", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/login";
    }

}
