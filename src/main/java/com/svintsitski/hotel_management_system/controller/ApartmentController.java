package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.dao.ApartmentTypeDaoImpl;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.service.ApartmentTypeService;
import com.svintsitski.hotel_management_system.service.ApartmentTypeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/apartment")
public class ApartmentController {

    @Autowired
    private ApartmentTypeServiceImpl apartmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping(value = {"/", "/list", ""})
    public String getAll(Model model) {
        List<ApartmentType> list = apartmentService.getAll();
        model.addAttribute("apartment_list", list);
        return "apartment_list";
    }
}
