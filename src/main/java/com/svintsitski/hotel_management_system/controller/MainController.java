package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.support.URL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    public static int default_page_size = 5;

    @RequestMapping("/")
    public String welcome(HttpServletRequest request,
                          Model model) {

        URL.IPInfo("", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("config", Config.getInstance());
        return "index";
    }

}