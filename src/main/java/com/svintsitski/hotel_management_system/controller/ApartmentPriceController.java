package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.Pagination;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import com.svintsitski.hotel_management_system.model.URL;
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
@RequestMapping("/admin/apartment/price")
public class ApartmentPriceController {

    @Autowired
    private ApartmentTypeServiceImpl apartmentService;

    String mainObject = "apartment_price";
    String relativeURL = "admin/" + mainObject;
    String redirectURL = "redirect:/admin/apartment/price/";
    String jspAdd = relativeURL + "_add";

    @GetMapping(value = {"/list/", "/list", "/", ""})
    public String findAll(@RequestParam Optional<Integer> page,
                          @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort,
                          Model model,
                          HttpServletRequest request) {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(default_page_size));

        ResultQuery result = apartmentService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);
        int full_elem_count = result.getCount();
        List<ApartmentType> list = result.getList();
        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo(relativeURL + "/list/", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("apartment_list", list);
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());
        model.addAttribute("sort", sorting);

        return relativeURL;
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id,
                             HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        ApartmentType apartmentType = apartmentService.findById(id);

        URL.IPInfo(relativeURL + "/update/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject("apartmentType", apartmentType);
        model.setViewName(jspAdd);
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        ApartmentType apartmentType = new ApartmentType();

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject("apartmentType", apartmentType);
        model.setViewName(jspAdd);
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("apartmentType") ApartmentType apartmentType,
                             HttpServletRequest request) {

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.POST);

        if (apartmentService.findById(apartmentType.getId()) != null) {
            System.out.println(0);
            apartmentService.update(apartmentType);
        } else {
            System.out.println(1);
            apartmentService.add(apartmentType);
        }
        return new ModelAndView(redirectURL);
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id,
                               HttpServletRequest request) {

        URL.IPInfo(relativeURL + "/delete/", request.getRemoteAddr(), RequestMethod.GET);
        apartmentService.delete(id);

        return new ModelAndView(redirectURL);
    }
}
