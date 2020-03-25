package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import com.svintsitski.hotel_management_system.service.ApartmentTypeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/apartment")
public class ApartmentController {

    @Autowired
    private ApartmentTypeServiceImpl apartmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping(value = {"/", "/list", ""}) //page, limit http://localhost:8184/admin/apartment/?start=1&total=10
    //localhost:8184/admin/apartment/?page=1
    public String findAll(@RequestParam Optional<Integer> page, Model model, HttpServletRequest request) {

        int try_page = page.orElse(1);
        int elemCount = 10;
        int start = 1 + (try_page - 1) * elemCount;

        ResultQuery result = apartmentService.findAll(start, elemCount);
        int full_elem_count = result.getCount();
        List<ApartmentType> list = result.getApartmentTypeList();

        int total_page = (int) Math.ceil((float)full_elem_count/(float)elemCount);
        total_page = Math.max(total_page, 1);

        String url = "http://localhost:8184/admin/apartment/";
        String ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested apartment_list.jsp");
        LOGGER.info("URL = " + url);
        LOGGER.info("total_page = " + total_page);
        LOGGER.info("current_page = " + try_page);
        LOGGER.info("full_elem_count = " + full_elem_count);

        model.addAttribute("apartment_list", list);
        model.addAttribute("current_page", try_page);
        model.addAttribute("total_page", total_page);
        model.addAttribute("path", url);

        return "apartment_list";
    }

    @GetMapping(value = {"/find/", "/find"})
    public String find(@RequestParam int start, @RequestParam int total, Model model) {
        List<ApartmentType> list = apartmentService.findByType("простой", start, total);
        model.addAttribute("apartment_list", list);
        return "apartment_list";
    }
}
