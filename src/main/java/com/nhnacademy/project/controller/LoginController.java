package com.nhnacademy.project.controller;

import com.nhnacademy.project.domain.UserGrade;
import com.nhnacademy.project.exception.WrongLoginInfoException;
import com.nhnacademy.project.repository.SessionRepository;
import com.nhnacademy.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final SessionRepository sessionRepository;

    public LoginController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public String login(HttpServletRequest request, Model model) {
        StringBuilder sb = new StringBuilder("thymeleaf/loginForm");
        HttpSession session = request.getSession(false);
        if (Objects.isNull(session)) {
            return sb.toString();
        }
        String login = (String) session.getAttribute("login");

        if(Objects.isNull(login)){
            return sb.toString();
        }

        if (userRepository.exists(login)) {
            model.addAttribute("id", login);

            classifyUrlByUserGrade(sb, login, false);
        }
        return sb.toString();
    }

    private void classifyUrlByUserGrade(StringBuilder sb, String id, boolean shouldRedirect) {
        UserGrade grade = userRepository.getUser(id).getGrade();
        sb.delete(0, sb.length());
        if (shouldRedirect) {
            sb.append("redirect:");
        }

        if (grade.equals(UserGrade.CUSTOMER)) {
            sb.append("/cs");
            return;
        }
        sb.append("/cs/admin");
    }


    @PostMapping
    public String doLogin(@RequestParam("id") String id,
                          @RequestParam("pwd") String pwd,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("redirect:/");
        if (id.length() == 0 || pwd.length() == 0) {
            return sb.toString();
        }

        verifyLoginInfo(id, pwd);


        createLoginState(id, request, response);
        classifyUrlByUserGrade(sb, id, true);
        return sb.toString();
    }

    private void verifyLoginInfo(String id, String pwd) {
        if (!userRepository.matches(id, pwd)) {
            throw new WrongLoginInfoException();
        }
    }

    private void createLoginState(String id, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Cookie cookie = new Cookie("SESSION", session.getId());
        response.addCookie(cookie);
        session.setAttribute("login", id);
        sessionRepository.add(session.getId(), session);
    }

}
