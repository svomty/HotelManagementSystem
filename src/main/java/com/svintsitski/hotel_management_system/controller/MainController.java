package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    public static int default_page_size = 5;

    @RequestMapping("/")
    public String welcome(HttpServletRequest request) {

        URL.IPInfo("", request.getRemoteAddr(), RequestMethod.GET);

        return "index";
    }

}