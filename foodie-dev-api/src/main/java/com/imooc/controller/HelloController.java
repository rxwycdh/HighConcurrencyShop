package com.imooc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class HelloController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Object hello() {
        return "hello";
    }

    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("userInfo", "new user");
        // 设置过期时间 0为永不过期
        httpSession.setMaxInactiveInterval(3600);
        httpSession.getAttribute("userInfo");
        return "ok";
    }

}
