package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.config.jsp.Operation;
import com.svintsitski.hotel_management_system.config.jsp.SiteConfig;
import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.support.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    @Autowired
    public InMemoryUserDetailsManager inMemoryUserDetailsManager;

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

        inMemoryUserDetailsManager.deleteUser(Config.getInstance().getLogin());

        Config.getInstance().overwriteProperties(config);

        Config config1 = Config.getInstance();

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        inMemoryUserDetailsManager.createUser(users.username(config1.getLogin())
                .password(config1.getPassword()).roles("ADMIN").build());


        return new ModelAndView(redirectURL);
    }
}
