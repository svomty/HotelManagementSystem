package com.svintsitski.hotel_management_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/")
    public String welcome(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        LOGGER.info("[" + ip + "] requested index.jsp");
        return "index";
    }

}