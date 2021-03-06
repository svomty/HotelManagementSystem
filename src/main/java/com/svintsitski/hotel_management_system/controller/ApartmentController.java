package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.support.Pagination;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import com.svintsitski.hotel_management_system.model.support.URL;
import com.svintsitski.hotel_management_system.model.support.View;
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
                          @RequestParam Optional<String> type,
                          @RequestParam Optional<Integer> place,
                          @RequestParam Optional<Integer> room,
                          Model model,
                          HttpServletRequest request) throws Exception {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        ResultQuery result = apartmentService
                .findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);

        List<Apartment> apartments = (List<Apartment>) result.getList();

        //удаление
        if (apartments.isEmpty()) {
            pagination = new Pagination(pagination.getTotalPage(result.getCount()), size.orElse(Config.getInstance().getCountElem()));

            result = apartmentService
                    .findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);
        }
        //удаление
//фильтрация
        if (type.isPresent() || place.isPresent() || room.isPresent()) {

            result = apartmentService.filter(pagination.getStartElem(), pagination.getPage_size(), sorting,
                    type.orElse(""), place.orElse(0), room.orElse(0));
        }
//фильтрация
        int full_elem_count = result.getCount();
        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo(relativeURL + "/list/", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("apartment_list", result.getList().get(0));
        model.addAttribute("apartment_type_list", result.getList().get(1));
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("view", new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sorting));
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());
        model.addAttribute("sort", sorting);
        model.addAttribute("type", type.orElse(""));
        model.addAttribute("place", place.orElse(0));
        model.addAttribute("room", room.orElse(0));
        model.addAttribute("config", Config.getInstance());
        return relativeURL;
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id,
                             @RequestParam Optional<Integer> page,
                             @RequestParam Optional<Integer> size,
                             @RequestParam Optional<String> sort,
                             HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Apartment apartment = apartmentService.findById(id);

        ResultQuery resultQuery = apartmentTypeService.findAll(1, 1000, "id");
        List<ApartmentType> apartmentTypes = resultQuery.getList();

        URL.IPInfo(relativeURL + "/update/", request.getRemoteAddr(), RequestMethod.GET);

        View view = new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sort.orElse("id"));
        model.addObject("view", view);

        model.addObject(mainObject, apartment);
        model.addObject("apartmentType", apartmentTypes);
        model.setViewName(jspAdd);
        model.addObject("config", Config.getInstance());

        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request,
                            @ModelAttribute("view") View view) {
        ModelAndView model = new ModelAndView();
        Apartment apartment = new Apartment();

        ResultQuery resultQuery = apartmentTypeService.findAll(1, 1000, "id");
        List<ApartmentType> apartmentTypes = resultQuery.getList();

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.GET);
        model.addObject("view", view);

        model.addObject(mainObject, apartment);
        model.addObject("apartmentType", apartmentTypes);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("apartment") Apartment apartment,
                             @ModelAttribute("view") View view,
                             HttpServletRequest request) {

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.POST);

        if (apartmentService.findById(apartment.getId()) != null) {
            apartmentService.update(apartment);
        } else {
            apartmentService.add(apartment);
        }

        return new ModelAndView(redirectURL + "/?page=" + view.getPage() + "&size=" + view.getSize()
                + "&sort=" + view.getSort());
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id,
                               @RequestParam Optional<Integer> page,
                               @RequestParam Optional<Integer> size,
                               @RequestParam Optional<String> sort,
                               @RequestParam Optional<String> type,
                               @RequestParam Optional<String> place,
                               @RequestParam Optional<String> room,
                               HttpServletRequest request) {

        URL.IPInfo(relativeURL + "/delete/", request.getRemoteAddr(), RequestMethod.GET);
        apartmentService.delete(id);

        return new ModelAndView(redirectURL + "/?page=" + page.get() + "&size=" + size.get() + "&sort="
                + sort.get() + "&type=" + type.orElse("") + "&place=" + place.orElse("")
                + "&room=" + room.orElse(""));
    }
}
