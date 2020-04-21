package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.enam.Activity;
import com.svintsitski.hotel_management_system.model.support.Checker;
import com.svintsitski.hotel_management_system.model.support.Pagination;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import com.svintsitski.hotel_management_system.model.support.URL;
import com.svintsitski.hotel_management_system.service.ApartmentService;
import com.svintsitski.hotel_management_system.service.ApartmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private ApartmentTypeService apartmentTypeService;

    @RequestMapping("/")
    public String welcome(HttpServletRequest request,
                          Model model) {

        URL.IPInfo("", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("config", Config.getInstance());
        return "index";
    }

    @RequestMapping("/reservation/")
    public String reservation(HttpServletRequest request,
                              @RequestParam Optional<Integer> page,
                              @RequestParam Optional<Integer> size,
                              @RequestParam Optional<String> arrival_date_filter,
                              @RequestParam Optional<String> departure_date_filter,
                              Model model) throws Exception {

        String sorting = "id";
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        List<Date> dates = Checker.validateDateForAccommodation(arrival_date_filter, departure_date_filter);

        //ResultQuery result = apartmentService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);
        ResultQuery result = apartmentService.findForDate(dates.get(0), dates.get(1), Activity.Reservation, 0);
        //find for date ИЗМЕНИТЬ
        int full_elem_count = result.getCount();
        //инициализировали листы
        List<Apartment> apartmentList = (List<Apartment>) result.getList().get(0);
        List<ApartmentType> apartmentTypes = (List<ApartmentType>) result.getList().get(1);

        //пошли расчеты куда-нибудь их перекинуть

        List<Integer> apartmentListCounter = new ArrayList<>();
        List<ApartmentType> apartmentTypesFree = new ArrayList<>();

        for (int i = 0; i < apartmentList.size(); i++) {
            int id = apartmentTypes.get(i).getId();

            Optional<ApartmentType> r = apartmentTypesFree
                    .stream()
                    .filter(m -> id == m.getId())
                    .findFirst();

            if (!r.isPresent()) {
                apartmentListCounter.add(1);
                apartmentTypesFree.add(apartmentTypes.get(i));
            } else {
                for (int j = 0; j < apartmentTypesFree.size(); j++) {
                    if (apartmentTypesFree.get(j).getId() == r.get().getId()) {
                        apartmentListCounter.set(j, apartmentListCounter.get(j) + 1);
                        break;
                    }
                }
            }
        }

        //apartments.size(), Arrays.asList(apartments, apartmentType, totalPlaces)
        apartmentTypesFree.forEach(System.out::println);
        apartmentListCounter.forEach(System.out::println);
        System.out.println("mnmnmnm");
        //рассчитали количество номеров с этим типом
        //int total_page = pagination.getTotalPage(full_elem_count);

        int start = pagination.getStartElem();
        int total = pagination.getPage_size();

        int total_page = pagination.getTotalPage(apartmentTypesFree.size());

        System.out.println("-------------------");
        System.out.println("start" + start);
        System.out.println("total" + total);
        System.out.println("total_page" + total_page);
        System.out.println("size" + apartmentTypesFree.size());
        System.out.println("getStartElem" + pagination.getStartElem());
        System.out.println("getCurrent_page" + pagination.getCurrent_page());
        System.out.println("getPage_size" + pagination.getPage_size());
        System.out.println("getStart_page" + pagination.getStart_page());
        System.out.println("-------------------");

        apartmentTypesFree = new ArrayList<>(apartmentTypesFree.subList(pagination.getStartElem() - 1, total));

        URL.IPInfo("", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("apartment_list", apartmentTypesFree);
        model.addAttribute("counter", apartmentListCounter);
        model.addAttribute("counterTotal", apartmentListCounter.stream().reduce(0, Integer::sum));
        model.addAttribute("arrival_date_filter", dates.get(0));
        model.addAttribute("departure_date_filter", dates.get(1));
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());

        model.addAttribute("config", Config.getInstance());
        return "reservation";
    }

    @GetMapping(value = "/reservation/{id}")
    public ModelAndView addReservation(@PathVariable int id,

                                       HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        //ApartmentType apartmentType = apartmentTypeService.findById(id);
        List<Apartment> apartments = apartmentService.findByType(id);


        model.addObject("config", Config.getInstance());
        return model;
    }
}