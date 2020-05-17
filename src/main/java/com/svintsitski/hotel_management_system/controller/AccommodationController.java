package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.dao.ReservationDao;
import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Accommodation;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.enam.Activity;
import com.svintsitski.hotel_management_system.model.support.*;
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
    private ReservationDao reservationDao;
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

        //удаление
        if (accommodationList.size() == 0) {
            pagination = new Pagination(pagination.getTotalPage(result.getCount()), size.orElse(Config.getInstance().getCountElem()));

            result = accommodationService.
                    findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);

            accommodationList = (List<Accommodation>) result.getList().get(0);
        }
        //удаление

        int total_page = pagination.getTotalPage(result.getCount());

        URL.IPInfo(relativeURL + "list/", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("accommodation_list", accommodationList);
        model.addAttribute("customers", result.getList().get(1));
        model.addAttribute("apartments", result.getList().get(2));
        model.addAttribute("view", new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sorting));
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
                             @RequestParam Optional<Integer> page,
                             @RequestParam Optional<Integer> size,
                             @RequestParam Optional<String> sort,
                             @RequestParam Optional<String> arrival_date_filter,
                             @RequestParam Optional<String> departure_date_filter) throws Exception {
        ModelAndView model = new ModelAndView();
        Accommodation accommodation = accommodationService.findById(id);

        List<Date> dates = Checker.validateDateForAccommodation(arrival_date_filter, departure_date_filter);

        List customerList = customerService.findAll("id");
        List apartmentList = apartmentService.findForDate(dates.get(0), dates.get(1), Activity.Accommodation, id).getList();

        URL.IPInfo(relativeURL + "update/", request.getRemoteAddr(), RequestMethod.GET);

        View view = new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sort.orElse("id"));
        model.addObject("view", view);

        model.addObject("arrival_date_filter", dates.get(0));
        model.addObject("departure_date_filter", dates.get(1));
        model.addObject("totalPlaces", apartmentList.get(2));
        model.addObject("customerList", customerList);
        model.addObject("identity", new Identity());
        model.addObject("apartmentList", apartmentList.get(0));
        model.addObject("apartmentTypeList", apartmentList.get(1));
        model.addObject(mainObject, accommodation);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request,
                            @RequestParam Optional<String> arrival_date_filter,
                            @RequestParam Optional<String> departure_date_filter,
                            @RequestParam Optional<String> apartment,
                            @ModelAttribute("view") View view,
                            @RequestParam Optional<String> full_name,
                            @RequestParam Optional<String> reservation) throws Exception {
        ModelAndView model = new ModelAndView();
        Accommodation accommodation = new Accommodation();

        List<Date> dates = Checker.validateDateForAccommodation(arrival_date_filter, departure_date_filter);

        List customerList = customerService.findAll("id");
        List apartmentList;
        Identity identity = new Identity();

        if (apartment.isPresent()) {
            apartmentList = apartmentService.findForDate(dates.get(0), dates.get(1), Activity.ReservationToAccommodation,
                    Integer.parseInt(apartment.get())).getList();
            accommodation.setApartment_id(Integer.parseInt(apartment.get()));
        } else {
            apartmentList = apartmentService.findForDate(dates.get(0), dates.get(1), Activity.Accommodation, 0).getList();
        }

        reservation.ifPresent(s -> identity.setIdentificator(Integer.parseInt(s)));

        String name = full_name.orElse("");

        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject("arrival_date_filter", dates.get(0));
        model.addObject("full_name", name);
        model.addObject("view", view);
        model.addObject("departure_date_filter", dates.get(1));
        model.addObject("totalPlaces", apartmentList.get(2));
        model.addObject("customerList", customerList);
        model.addObject("identity", identity);
        model.addObject("apartmentList", apartmentList.get(0));
        model.addObject("apartmentTypeList", apartmentList.get(1));
        model.addObject(mainObject, accommodation);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("hotelAccommodation") Accommodation accommodation,
                             @ModelAttribute("view") View view,
                             @ModelAttribute("identity") Optional<Identity> reservation,
                             HttpServletRequest request) {

        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.POST);

        if (accommodationService.findById(accommodation.getId()) != null) {
            accommodationService.update(accommodation);
        } else {
            accommodationService.add(accommodation);
        }
        if (reservation.isPresent()) {
            int reservationId = reservation.get().getIdentificator();
            System.out.println(reservationId);
            if (reservationId != 0) {
                Reservation reservation1 = reservationDao.findById(reservationId);
                reservation1.setArrived((byte) 1);
                reservationDao.update(reservation1);
            }

        }
        return new ModelAndView(redirectURL + "/?page=" + view.getPage() + "&size=" + view.getSize()
                + "&sort=" + view.getSort());
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id,
                               @RequestParam Optional<Integer> page,
                               @RequestParam Optional<Integer> size,
                               @RequestParam Optional<String> sort,
                               HttpServletRequest request) {

        URL.IPInfo(relativeURL + "delete/", request.getRemoteAddr(), RequestMethod.GET);
        accommodationService.delete(id);

        return new ModelAndView(redirectURL + "?page=" + page.get() + "&size=" + size.get() + "&sort=" + sort.get());
    }
}
