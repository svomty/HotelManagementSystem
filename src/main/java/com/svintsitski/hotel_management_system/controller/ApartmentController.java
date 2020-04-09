package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.ServingWebContentApplication;
import com.svintsitski.hotel_management_system.model.*;
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

import static com.svintsitski.hotel_management_system.controller.MainController.default_page_size;

@Controller
@RequestMapping("/admin/apartment")
public class ApartmentController {
    @Autowired
    private ApartmentServiceImpl apartmentService;
    @Autowired
    private ApartmentTypeServiceImpl apartmentTypeService;

    String relativeURL = "admin/apartment/";
    String redirectURL = "redirect:/"+relativeURL;

    @GetMapping(value = {"/list/", "/list", "/"})
    public String findAll(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort, Model model, HttpServletRequest request) {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(default_page_size));

        ResultQuery result = apartmentService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);
        int full_elem_count = result.getCount();
        List resultQueryList = result.getList();
        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo(relativeURL+"list/", request.getRemoteAddr(), "GET");
        String createURL = URL.generateURL("admin/apartment/add");

        model.addAttribute("apartment_list", resultQueryList.get(0));
        model.addAttribute("createURL", createURL);
        model.addAttribute("apartment_type_list", resultQueryList.get(1));
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());
        model.addAttribute("sort", sorting);

        return "admin_apartment";
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Apartment apartment = apartmentService.findById(id);
        List<ApartmentType> apartmentType = apartmentTypeService.findAll(1, 1000, "id").getList();

        URL.IPInfo(relativeURL+"update/", request.getRemoteAddr(), "GET");

        model.addObject("apartment", apartment);
        model.addObject("apartmentType", apartmentType);
        model.setViewName("admin_apart_add");
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Apartment apartment = new Apartment();
        List<ApartmentType> apartmentType = apartmentTypeService.findAll(1, 1000, "id").getList();

        URL.IPInfo(relativeURL+"add/", request.getRemoteAddr(), "GET");

        model.addObject("apartment", apartment);
        model.addObject("apartmentType", apartmentType);
        model.setViewName("admin_apart_add");
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("apartment") Apartment apartment, HttpServletRequest request) {

        URL.IPInfo(relativeURL+"add/", request.getRemoteAddr(), "POST");

        if (apartmentService.findById(apartment.getId()) != null) {
            apartmentService.update(apartment);
        } else {
            apartmentService.add(apartment);
        }

        return new ModelAndView(redirectURL);
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id, HttpServletRequest request) {

        URL.IPInfo(relativeURL+"delete/", request.getRemoteAddr(), "GET");
        apartmentService.delete(id);

        return new ModelAndView(redirectURL);
    }
}
