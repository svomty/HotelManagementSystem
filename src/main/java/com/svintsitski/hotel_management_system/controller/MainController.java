package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.support.Pagination;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import com.svintsitski.hotel_management_system.model.support.URL;
import com.svintsitski.hotel_management_system.service.ApartmentTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private ApartmentTypeServiceImpl apartmentService;

    @RequestMapping("/")
    public String welcome(HttpServletRequest request,
                          Model model) {

        URL.IPInfo("", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("config", Config.getInstance());
        return "index";
    }

    @RequestMapping("/reservation/")
    public String reservation(HttpServletRequest request,
                              @RequestParam Optional<Integer> page,
                              @RequestParam Optional<Integer> size,
                              Model model) {

        String sorting = "id";
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        ResultQuery result = apartmentService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);
        int full_elem_count = result.getCount();
        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo("", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("apartment_list", result.getList());
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());

        model.addAttribute("config", Config.getInstance());
        return "reservation";
    }
}