package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.ServingWebContentApplication;
import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import com.svintsitski.hotel_management_system.service.ApartmentServiceImpl;
import com.svintsitski.hotel_management_system.service.ApartmentTypeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentController.class);

    String url;
    String ip;

    @GetMapping(value = {"/list/", "/list", "/"})
    public String findAll(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort, Model model, HttpServletRequest request) {

        int current_page = page.orElse(1);
        String sorting = sort.orElse("id");
        int start_page = (current_page - 2) < 0 ? 0 : current_page - 2;
        int default_page_size = 5;
        int page_size = size.orElse(default_page_size);
        page_size = (page_size < 1) ? default_page_size : page_size;
        int start = 1 + (current_page - 1) * page_size;

        ResultQuery result = apartmentService.findAll(start, page_size, sorting);
        int full_elem_count = result.getCount();
        List resultQueryList = result.getList();

        int total_page = (int) Math.ceil((float)full_elem_count/(float)page_size);
        total_page = Math.max(total_page, 1);

        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/list/";
        ip = request.getRemoteAddr();
        String createURL = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/add";

        LOGGER.info("[" + ip + "] requested " + url);

        model.addAttribute("apartment_list", resultQueryList.get(0));
        model.addAttribute("createURL", createURL);
        model.addAttribute("apartment_type_list", resultQueryList.get(1));
        model.addAttribute("current_page", current_page);
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", page_size);
        model.addAttribute("start_page", start_page);
        model.addAttribute("sort", sorting);

        return "admin_apartment";
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Apartment apartment = apartmentService.findById(id);
        List<ApartmentType> apartmentType = apartmentTypeService.findAll(1, 1000, "id").getList();

        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/update/" + id;
        ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested " + url + ". Аpartment №" + apartment.getId() + " will be updated");

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

        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/add/";
        ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested " + url + ". Аpartment will be added");

        model.addObject("apartment", apartment);
        model.addObject("apartmentType", apartmentType);
        model.setViewName("admin_apart_add");
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("apartment") Apartment apartment, HttpServletRequest request) {
        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/add/";
        ip = request.getRemoteAddr();
        if (apartmentService.findById(apartment.getId()) != null) {
            apartmentService.update(apartment);
            LOGGER.info("[" + ip + "] requested " + url + ". Аpartment №" + apartment.getId() + " was updated");
        } else {
            apartmentService.add(apartment);
            LOGGER.info("[" + ip + "] requested " + url + ". Аpartment was created");
        }
        return new ModelAndView("redirect:/admin/apartment/");
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id, HttpServletRequest request) {
        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/delete/" + id;
        ip = request.getRemoteAddr();

        apartmentService.delete(id);

        LOGGER.info("[" + ip + "] requested " + url + ". Аpartment №" + id + " was deleted");
        return new ModelAndView("redirect:/admin/apartment/");
    }
}
