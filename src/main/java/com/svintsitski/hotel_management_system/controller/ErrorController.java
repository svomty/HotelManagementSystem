package com.svintsitski.hotel_management_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String url = request.getServletPath();
        String ip = request.getRemoteAddr();

        String info = "[" + ip + "] requested " + url;

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("statusCode", statusCode.toString());
            LOGGER.error(info + "; status code = " + statusCode.toString());
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
