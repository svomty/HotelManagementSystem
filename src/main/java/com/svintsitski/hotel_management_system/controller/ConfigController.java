package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.config.jsp.Operation;
import com.svintsitski.hotel_management_system.config.jsp.SiteConfig;
import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.support.URL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/config")
public class ConfigController {

    SiteConfig siteConfig;
    String mainObject = "config";
    String relativeURL = "admin/" + mainObject;
    String redirectURL = "redirect:/" + relativeURL;

    @GetMapping(value = {"/", ""})
    public ModelAndView edit(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        URL.IPInfo(relativeURL, request.getRemoteAddr(), RequestMethod.GET);

        siteConfig = new SiteConfig(Operation.GET);

        model.addObject(Config.getInstance());
        model.setViewName(relativeURL);
        return model;
    }

    @PostMapping(value = {"/", ""})
    public ModelAndView save(HttpServletRequest request,
                             @ModelAttribute("config") Config config) {

        URL.IPInfo(relativeURL, request.getRemoteAddr(), RequestMethod.POST);

        Config.getInstance().overwriteProperties(config);

        return new ModelAndView(redirectURL);
    }
}
