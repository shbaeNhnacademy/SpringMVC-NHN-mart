package com.nhnacademy.project.controller;

import com.nhnacademy.project.exception.WrongLoginInfoException;
import com.nhnacademy.project.repository.ManagerRepository;
import com.nhnacademy.project.repository.SessionRepository;
import com.nhnacademy.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/")
public class LoginController {
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final SessionRepository sessionRepository;

    public LoginController(UserRepository userRepository, ManagerRepository managerRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (!Objects.isNull(session)) {
            String login = (String) session.getAttribute("login");
            if (!Objects.isNull(login)) {
                model.addAttribute("id", login);
                if (userRepository.exists(login)) {
                    log.info(login);
                    return "/cs";
                } else if (managerRepository.exists(login)) {
                    return "/cs/admin";
                }
            }
        }
        return "thymeleaf/loginForm";
    }

    @PostMapping
    public String doLogin(@RequestParam("id") String id,
                          @RequestParam("pwd") String pwd,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        if (userRepository.matches(id, pwd)) {
            verifyLogin(id, request, response);
            return "redirect:/cs";
        } else if (managerRepository.matches(id, pwd)) {
            verifyLogin(id, request, response);
            return "redirect:/cs/admin";
        }
        throw new WrongLoginInfoException();


    }

    private void verifyLogin(String id, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Cookie cookie = new Cookie("SESSION", session.getId());
        response.addCookie(cookie);
        session.setAttribute("login", id);
        sessionRepository.add(session.getId(), session);

    }

}
