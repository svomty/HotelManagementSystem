package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.enam.Activity;
import com.svintsitski.hotel_management_system.model.support.Checker;
import com.svintsitski.hotel_management_system.model.support.Pagination;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import com.svintsitski.hotel_management_system.model.support.URL;
import com.svintsitski.hotel_management_system.service.ApartmentService;
import com.svintsitski.hotel_management_system.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private ReservationServiceImpl reservationService;

    @GetMapping("/")
    public ModelAndView welcome(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        URL.IPInfo("", request.getRemoteAddr(), RequestMethod.GET);

        java.util.Date d1 = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(d1.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String newDate = sdf.format(cal.getTime());
        java.sql.Date sqlDate2 = java.sql.Date.valueOf(newDate);

        model.addObject("config", Config.getInstance());
        model.addObject("arrival_date_filter", sqlDate);
        model.addObject("departure_date_filter", sqlDate2);
        model.setViewName("index");
        return model;
    }

    @GetMapping("/reservation/")
    public String reservation(HttpServletRequest request,
                              @RequestParam Optional<Integer> page,
                              @RequestParam Optional<Integer> size,
                              @RequestParam Optional<String> arrival_date_filter,
                              @RequestParam Optional<String> departure_date_filter,
                              Model model) throws Exception {

        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        List<Date> dates = Checker.validateDateForAccommodation(arrival_date_filter, departure_date_filter);

        ResultQuery result = apartmentService.findForDate(dates.get(0), dates.get(1), Activity.Reservation, 0);

        List<Apartment> apartmentList = (List<Apartment>) result.getList().get(0);
        List<ApartmentType> apartmentTypes = (List<ApartmentType>) result.getList().get(1);

        ResultQuery resultQuery = reservationService
                .findFreeForUsers(pagination.getStartElem(),
                        pagination.getPage_size(),
                        apartmentList,
                        apartmentTypes);

        List<Integer> apartmentListCounter = (List<Integer>) resultQuery.getList().get(1);
        List<ApartmentType> apartmentTypesFree = (List<ApartmentType>) resultQuery.getList().get(0);

        int total_page = pagination.getTotalPage(resultQuery.getCount());

        URL.IPInfo("/reservation/", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("reservation", new Reservation());
        model.addAttribute("apartment_list", apartmentTypesFree);
        model.addAttribute("counter", apartmentListCounter);
        model.addAttribute("counterTotal", resultQuery.getList().get(2));
        model.addAttribute("arrival_date_filter", dates.get(0));
        model.addAttribute("departure_date_filter", dates.get(1));
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());

        model.addAttribute("config", Config.getInstance());
        return "reservation";
    }

    @PostMapping(value = "/reservation/")
    public String addReservation(@ModelAttribute("reservation") Reservation reservation,
                                 HttpServletRequest request) throws Exception {
        int typeId = reservation.getApartment_id();

        ResultQuery result = apartmentService.findForDate(reservation.getArrival_date(),
                reservation.getDeparture_date(),
                Activity.Reservation,
                0);

        List<Apartment> apartmentList = (List<Apartment>) result.getList().get(0);

        Apartment apartment = reservationService
                .reservationForUsers(apartmentList,
                        typeId);

        if (apartment != null) {
            reservation.setApartment_id(apartment.getId());
            int infoId = reservationService.add(reservation);
            String UUID = reservationService.findById(infoId).getUUID();
            return "redirect:/reports/reservation_report/info/" + UUID;
        } else {
            return "error";
        }
    }
}

