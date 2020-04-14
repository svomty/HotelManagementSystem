package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.support.Pagination;
import com.svintsitski.hotel_management_system.model.support.URL;
import com.svintsitski.hotel_management_system.service.ApartmentServiceImpl;
import com.svintsitski.hotel_management_system.service.ApartmentTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/apartment")
public class ApartmentController {
    @Autowired
    private ApartmentServiceImpl apartmentService;
    @Autowired
    private ApartmentTypeServiceImpl apartmentTypeService;

    String mainObject = "apartment";
    String relativeURL = "admin/" + mainObject;
    String redirectURL = "redirect:/" + relativeURL;
    String jspAdd = relativeURL + "_add";

    @GetMapping(value = {"/list/", "/list", "/", ""})
    public String findAll(@RequestParam Optional<Integer> page,
                          @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort,
                          Model model,
                          HttpServletRequest request) {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        List<List<? extends Object>> result = apartmentService
                .findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);

        int full_elem_count = result.get(0).size();
        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo(relativeURL + "list/", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("apartment_list", result.get(0));
        model.addAttribute("apartment_type_list", result.get(1));
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());
        model.addAttribute("sort", sorting);

        model.addAttribute("config", Config.getInstance());
        return relativeURL;
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id,
                             HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Apartment apartment = apartmentService.findById(id);
        List apartmentType = apartmentTypeService.findAll(1, 1000, "id");

        URL.IPInfo(relativeURL + "update/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject(mainObject, apartment);
        model.addObject("apartmentType", apartmentType);
        model.setViewName(jspAdd);

        model.addObject("config", Config.getInstance());
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Apartment apartment = new Apartment();
        List apartmentType = apartmentTypeService.findAll(1, 1000, "id");

        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject(mainObject, apartment);
        model.addObject("apartmentType", apartmentType);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("apartment") Apartment apartment,
                             HttpServletRequest request) {

        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.POST);

        if (apartmentService.findById(apartment.getId()) != null) {
            apartmentService.update(apartment);
        } else {
            apartmentService.add(apartment);
        }

        return new ModelAndView(redirectURL);
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id,
                               HttpServletRequest request) {

        URL.IPInfo(relativeURL + "delete/", request.getRemoteAddr(), RequestMethod.GET);
        apartmentService.delete(id);

        return new ModelAndView(redirectURL);
    }
}
