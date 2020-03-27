package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import com.svintsitski.hotel_management_system.service.ApartmentTypeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/apartment")
public class ApartmentController {

    @Autowired
    private ApartmentTypeServiceImpl apartmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping(value = {"/price/list/", "/price/list"})
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

        String url = "http://localhost:8184/admin/apartment/price/list/";
        String ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested " + url);

        model.addAttribute("apartment_list", list);
        model.addAttribute("current_page", current_page);
        model.addAttribute("total_page", total_page);
        model.addAttribute("path", url);
        model.addAttribute("size", page_size);
        model.addAttribute("start_page", start_page);
        model.addAttribute("sort", sorting);

        return "apartment_list";
    }

}
