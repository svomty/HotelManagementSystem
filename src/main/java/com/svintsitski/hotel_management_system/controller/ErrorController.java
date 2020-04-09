package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.URL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        URL.IPInfo(request.getServletPath(), request.getRemoteAddr(), RequestMethod.GET);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("statusCode", statusCode.toString());
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
