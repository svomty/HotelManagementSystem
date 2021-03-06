package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.enam.MyString;
import com.svintsitski.hotel_management_system.model.enam.Type;
import com.svintsitski.hotel_management_system.model.support.Pagination;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import com.svintsitski.hotel_management_system.model.support.URL;
import com.svintsitski.hotel_management_system.model.support.View;
import com.svintsitski.hotel_management_system.service.ApartmentTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
                          @RequestParam Optional<String> type,
                          @RequestParam Optional<Integer> place,
                          @RequestParam Optional<Integer> room,
                          Model model,
                          HttpServletRequest request) throws Exception {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        ResultQuery result = apartmentService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);

        List<ApartmentType> apartmentTypes = (List<ApartmentType>) result.getList();

        //удаление
        if (apartmentTypes.isEmpty()) {
            pagination = new Pagination(pagination.getTotalPage(result.getCount()),
                    size.orElse(Config.getInstance().getCountElem()));

            result = apartmentService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);

            apartmentTypes = (List<ApartmentType>) result.getList();
        }
        //удаление
//фильтрация
        if (type.isPresent() || place.isPresent() || room.isPresent()) {

            String type2 = type.orElse("");

            if (type2.toLowerCase().equals("стандарт")) {
                type2 = "Простой";
            }

            type = Optional.of(type2);

            result = apartmentService.filter(pagination.getStartElem(), pagination.getPage_size(), sorting,
                    type.orElse(""), place.orElse(0), room.orElse(0));

            apartmentTypes = (List<ApartmentType>) result.getList();
        }
//фильтрация

        int full_elem_count = result.getCount();
        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo(relativeURL + "/list/", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("apartment_list", apartmentTypes);
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());
        model.addAttribute("sort", sorting);
        model.addAttribute("view", new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sorting));
        model.addAttribute("config", Config.getInstance());
        model.addAttribute("type", type.orElse(""));
        model.addAttribute("place", place.orElse(0));
        model.addAttribute("room", room.orElse(0));
        return relativeURL;
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id,
                             @RequestParam Optional<Integer> page,
                             @RequestParam Optional<Integer> size,
                             @RequestParam Optional<String> sort,
                             @RequestParam Optional<String> type,
                             HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        ApartmentType apartmentType = apartmentService.findById(id);

        URL.IPInfo(relativeURL + "/update/", request.getRemoteAddr(), RequestMethod.GET);

        View view = new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sort.orElse("id"));
        model.addObject("view", view);

        model.addObject("apartmentType", apartmentType);
        model.addObject("myString", new MyString(type.orElse("")));
        List<Type> types = Arrays.asList(Type.Люкс, Type.Простой);
        model.addObject("types", types);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request,
                            @RequestParam Optional<String> type,
                            @ModelAttribute("view") View view) {
        ModelAndView model = new ModelAndView();
        ApartmentType apartmentType = new ApartmentType();

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.GET);
        model.addObject("view", view);
        model.addObject("myString", new MyString(type.orElse("")));
        model.addObject("apartmentType", apartmentType);
        List<Type> types = Arrays.asList(Type.Люкс, Type.Простой);
        model.addObject("types", types);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("apartmentType") ApartmentType apartmentType,
                             @ModelAttribute("myString") MyString type,
                             @ModelAttribute("view") View view,
                             HttpServletRequest request) {

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.POST);

        if (apartmentService.findById(apartmentType.getId()) != null) {
            apartmentService.update(apartmentType);
        } else {
            apartmentService.add(apartmentType);
        }
        return new ModelAndView(redirectURL + "?page=" + view.getPage() + "&size=" + view.getSize()
                + "&sort=" + view.getSort());
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id,
                               @RequestParam Optional<Integer> page,
                               @RequestParam Optional<Integer> size,
                               @RequestParam Optional<String> sort,
                               @RequestParam Optional<String> type,
                               @RequestParam Optional<Integer> place,
                               @RequestParam Optional<Integer> room,
                               HttpServletRequest request) {

        URL.IPInfo(relativeURL + "/delete/", request.getRemoteAddr(), RequestMethod.GET);
        apartmentService.delete(id);

        return new ModelAndView(redirectURL + "?page=" + page.get() + "&size=" + size.get()
                + "&sort=" + sort.get() + "&type=" + type.orElse("") + "&place=" + place.orElse(null)
                + "&room=" + room.orElse(0));
    }
}
