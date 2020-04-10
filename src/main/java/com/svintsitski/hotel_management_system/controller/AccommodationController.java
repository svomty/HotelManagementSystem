package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.*;
import com.svintsitski.hotel_management_system.service.AccommodationServiceImpl;
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
@RequestMapping("/admin/accommodation")
public class AccommodationController {

    @Autowired private AccommodationServiceImpl hotelAccommodationService;

    String relativeURL = "admin/accommodation/";
    String redirectURL = "redirect:/" + relativeURL;
    String jsp = "admin_accommodation";
    String jspAdd = jsp + "_add";
    String mainObject = "accommodation";

    @GetMapping(value = {"/list/", "/list", "/", ""})
    public String findAll(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort, Model model, HttpServletRequest request) throws Exception {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(default_page_size));

        ResultQuery result = hotelAccommodationService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);
        int full_elem_count = result.getCount();
        List<Accommodation> list = result.getList();
        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo(relativeURL + "list/", request.getRemoteAddr(), RequestMethod.GET);
        String createURL = URL.generateURL(relativeURL + "add");

        model.addAttribute("accommodation_list", list);

        model.addAttribute("urlReturn", URL.generateURL(relativeURL));

        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());
        model.addAttribute("sort", sorting);

        return jsp;
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Accommodation hotelAccommodation = hotelAccommodationService.findById(id);

        URL.IPInfo(relativeURL + "update/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject(mainObject, hotelAccommodation);
        model.setViewName(jspAdd);
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Accommodation hotelAccommodation = new Accommodation();

        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject(mainObject, hotelAccommodation);
        model.setViewName(jspAdd);
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("hotelAccommodation") Accommodation hotelAccommodation,
                             HttpServletRequest request) {

        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.POST);

        if (hotelAccommodationService.findById(hotelAccommodation.getId()) != null) {
            hotelAccommodationService.update(hotelAccommodation);
        } else {
            hotelAccommodationService.add(hotelAccommodation);
        }
        return new ModelAndView(redirectURL);
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id, HttpServletRequest request) {

        URL.IPInfo(relativeURL + "delete/", request.getRemoteAddr(), RequestMethod.GET);
        hotelAccommodationService.delete(id);

        return new ModelAndView(redirectURL);
    }
}
