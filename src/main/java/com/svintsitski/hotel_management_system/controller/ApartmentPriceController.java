package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.ServingWebContentApplication;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import com.svintsitski.hotel_management_system.service.ApartmentTypeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/apartment/price")
public class ApartmentPriceController {

    @Autowired
    private ApartmentTypeServiceImpl apartmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

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
        List<ApartmentType> list = result.getList();

        int total_page = (int) Math.ceil((float)full_elem_count/(float)page_size);
        total_page = Math.max(total_page, 1);

        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/price/list/";
        ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested " + url);

        model.addAttribute("apartment_list", list);
        model.addAttribute("current_page", current_page);
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", page_size);
        model.addAttribute("start_page", start_page);
        model.addAttribute("sort", sorting);

        return "admin_apartment_price";
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        ApartmentType apartmentType = apartmentService.findById(id);

        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/price/update/" + id;
        ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested " + url + ". АpartmentType №" + apartmentType.getId() + " will be updated");

        model.addObject("apartmentType", apartmentType);
        model.setViewName("admin_price_add");
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        ApartmentType apartmentType = new ApartmentType();

        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/price/add/";
        ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested " + url + ". АpartmentType will be added");

        model.addObject("apartmentType", apartmentType);
        model.setViewName("admin_price_add");
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("apartmentType") ApartmentType apartmentType, HttpServletRequest request) {
        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/price/add/";
        ip = request.getRemoteAddr();
        if (apartmentService.findById(apartmentType.getId()) != null) {
            apartmentService.update(apartmentType);
            LOGGER.info("[" + ip + "] requested " + url + ". АpartmentType was created");
        } else {
            apartmentService.add(apartmentType);
            LOGGER.info("[" + ip + "] requested " + url + ". АpartmentType №" + apartmentType.getId() + " was updated");
        }
        return new ModelAndView("redirect:/admin/apartment/price/");
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id, HttpServletRequest request) {
        url = ServingWebContentApplication.DOMAIN_FULL + "admin/apartment/price/delete/" + id;
        ip = request.getRemoteAddr();

        apartmentService.delete(id);

        LOGGER.info("[" + ip + "] requested " + url + ". АpartmentType №" + id + " was deleted");
        return new ModelAndView("redirect:/admin/apartment/price/");
    }
}
