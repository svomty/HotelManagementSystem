package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Accommodation;
import com.svintsitski.hotel_management_system.model.support.Checker;
import com.svintsitski.hotel_management_system.model.support.Pagination;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import com.svintsitski.hotel_management_system.model.support.URL;
import com.svintsitski.hotel_management_system.service.AccommodationServiceImpl;
import com.svintsitski.hotel_management_system.service.ApartmentServiceImpl;
import com.svintsitski.hotel_management_system.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/accommodation")
public class AccommodationController {

    @Autowired
    private AccommodationServiceImpl accommodationService;
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private ApartmentServiceImpl apartmentService;

    String mainObject = "accommodation";
    String relativeURL = "admin/" + mainObject;
    String redirectURL = "redirect:/" + relativeURL;
    String jspAdd = relativeURL + "_add";

    @GetMapping(value = {"/list/", "/list", "/", ""})
    public String findAll(@RequestParam Optional<Integer> page,
                          @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort,
                          Model model,
                          HttpServletRequest request) throws Exception {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        ResultQuery result = accommodationService.
                findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);
        List<Accommodation> accommodationList = (List<Accommodation>) result.getList().get(0);

        int total_page = pagination.getTotalPage(result.getCount());

        URL.IPInfo(relativeURL + "list/", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("accommodation_list", accommodationList);
        model.addAttribute("customers", result.getList().get(1));
        model.addAttribute("apartments", result.getList().get(2));

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
                             HttpServletRequest request,
                             @RequestParam Optional<String> arrival_date_filter,
                             @RequestParam Optional<String> departure_date_filter) throws Exception {
        ModelAndView model = new ModelAndView();
        Accommodation hotelAccommodation = accommodationService.findById(id);

        List<Date> dates = Checker.validateDateForAccommodation(arrival_date_filter, departure_date_filter);

        List customerList = customerService.findAll(1, 1000, "id");
        List apartmentList = apartmentService.findForDate(dates.get(0), dates.get(1)).getList();

        URL.IPInfo(relativeURL + "update/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject("arrival_date_filter", dates.get(0));
        model.addObject("departure_date_filter", dates.get(1));
        model.addObject("totalPlaces", apartmentList.get(2));
        model.addObject("customerList", customerList);
        model.addObject("apartmentList", apartmentList.get(0));
        model.addObject("apartmentTypeList", apartmentList.get(1));
        model.addObject(mainObject, hotelAccommodation);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request,
                            @RequestParam Optional<String> arrival_date_filter,
                            @RequestParam Optional<String> departure_date_filter) throws Exception {
        ModelAndView model = new ModelAndView();
        Accommodation hotelAccommodation = new Accommodation();

        List<Date> dates = Checker.validateDateForAccommodation(arrival_date_filter, departure_date_filter);

        List customerList = customerService.findAll(1, 1000, "id");
        List apartmentList = apartmentService.findForDate(dates.get(0), dates.get(1)).getList();

        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject("arrival_date_filter", dates.get(0));
        model.addObject("departure_date_filter", dates.get(1));
        model.addObject("totalPlaces", apartmentList.get(2));
        model.addObject("customerList", customerList);
        model.addObject("apartmentList", apartmentList.get(0));
        model.addObject("apartmentTypeList", apartmentList.get(1));
        model.addObject(mainObject, hotelAccommodation);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("hotelAccommodation") Accommodation hotelAccommodation,
                             HttpServletRequest request) {

        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.POST);

        if (accommodationService.findById(hotelAccommodation.getId()) != null) {
            accommodationService.update(hotelAccommodation);
        } else {
            accommodationService.add(hotelAccommodation);
        }
        return new ModelAndView(redirectURL);
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id,
                               HttpServletRequest request) {

        URL.IPInfo(relativeURL + "delete/", request.getRemoteAddr(), RequestMethod.GET);
        accommodationService.delete(id);

        return new ModelAndView(redirectURL);
    }
}
