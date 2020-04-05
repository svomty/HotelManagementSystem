package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.ServingWebContentApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    String url;
    String ip;
    public static int default_page_size = 5;

    @RequestMapping("/")
    public String welcome(HttpServletRequest request) {

        url = ServingWebContentApplication.DOMAIN_FULL;
        ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested " + url);

        return "index";
    }

}