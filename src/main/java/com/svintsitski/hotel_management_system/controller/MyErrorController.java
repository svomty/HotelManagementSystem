package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.support.URL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("error")
    public String handleError(HttpServletRequest request,
                              Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message;
        Object uri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        URL.IPInfo(request.getServletPath(), request.getRemoteAddr(), RequestMethod.GET);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("statusCode", statusCode.toString());
        }

        Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (exception != null) {
            message = exception.getMessage();
        } else {
            message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        }

        model.addAttribute("config", Config.getInstance());
        model.addAttribute("uri", uri.toString());
        model.addAttribute("message", message.toString());
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
