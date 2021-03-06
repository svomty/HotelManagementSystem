package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.enam.Activity;
import com.svintsitski.hotel_management_system.model.support.*;
import com.svintsitski.hotel_management_system.service.ApartmentServiceImpl;
import com.svintsitski.hotel_management_system.service.ReservationServiceImpl;
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
@RequestMapping("/admin/reservation")
public class ReservationController {
    @Autowired
    private ReservationServiceImpl reservationService;
    @Autowired
    private ApartmentServiceImpl apartmentService;

    String mainObject = "reservation";
    String relativeURL = "admin/" + mainObject;
    String redirectURL = "redirect:/" + relativeURL;
    String jspAdd = relativeURL + "_add";

    @GetMapping(value = {"/list/", "/list", "/", ""})
    public String findAll(@RequestParam Optional<Integer> page,
                          @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort,
                          @RequestParam Optional<String> fio,
                          @RequestParam Optional<String> date,
                          @RequestParam Optional<String> phone,
                          Model model,
                          HttpServletRequest request) throws Exception {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        ResultQuery result = reservationService.
                findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);

        List<Reservation> reservationList = (List<Reservation>) result.getList().get(0);

        //удаление
        if (reservationList.isEmpty()) {
            pagination = new Pagination(pagination.getTotalPage(result.getCount()), size.orElse(Config.getInstance().getCountElem()));

            result = reservationService.
                    findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);

            reservationList = (List<Reservation>) result.getList().get(0);
        }
        //удаление

//фильтрация
        if (phone.isPresent() || date.isPresent() || fio.isPresent()) {

            result = reservationService.filter(pagination.getStartElem(), pagination.getPage_size(), sorting,
                    fio.orElse(""), date.orElse(""), phone.orElse(""));

            reservationList = (List<Reservation>) result.getList().get(0);
        }
//фильтрация

        int total_page = pagination.getTotalPage(result.getCount());

        URL.IPInfo(relativeURL + "/list/", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("reservation_list", reservationList);
        model.addAttribute("apartments", result.getList().get(1));
        model.addAttribute("view", new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sorting));
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());
        model.addAttribute("sort", sorting);
        model.addAttribute("fio", fio.orElse(""));
        model.addAttribute("date", date.orElse(""));
        model.addAttribute("phone", phone.orElse(""));
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
        Reservation reservation = reservationService.findById(id);

        List<Date> dates = Checker.validateDateForAccommodation(arrival_date_filter, departure_date_filter);

        List apartmentList = apartmentService.findForDate(dates.get(0), dates.get(1), Activity.Reservation,
                id).getList();

        Checker checker = new Checker(reservation.getArrived() == 1);

        URL.IPInfo(relativeURL + "/update/", request.getRemoteAddr(), RequestMethod.GET);

        View view = new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sort.orElse("id"));
        model.addObject("view", view);

        model.addObject("checker", checker);
        model.addObject("arrival_date_filter", dates.get(0));
        model.addObject("departure_date_filter", dates.get(1));
        model.addObject("totalPlaces", apartmentList.get(2));
        model.addObject("apartmentList", apartmentList.get(0));
        model.addObject("apartmentTypeList", apartmentList.get(1));
        model.addObject(mainObject, reservation);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request,
                            @RequestParam Optional<String> arrival_date_filter,
                            @ModelAttribute("view") View view,
                            @RequestParam Optional<String> departure_date_filter) throws Exception {
        ModelAndView model = new ModelAndView();
        Reservation reservation = new Reservation();
        Checker checker = new Checker();

        List<Date> dates = Checker.validateDateForAccommodation(arrival_date_filter, departure_date_filter);

        List apartmentList = apartmentService.findForDate(dates.get(0), dates.get(1), Activity.Reservation, 0).getList();

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.GET);
        model.addObject("view", view);

        model.addObject("checker", checker);
        model.addObject("arrival_date_filter", dates.get(0));
        model.addObject("departure_date_filter", dates.get(1));
        model.addObject("totalPlaces", apartmentList.get(2));
        model.addObject("apartmentList", apartmentList.get(0));
        model.addObject("apartmentTypeList", apartmentList.get(1));
        model.addObject(mainObject, reservation);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("reservation") Reservation reservation,
                             @ModelAttribute("view") View view,
                             @ModelAttribute("checker") Optional<Checker> checker,
                             HttpServletRequest request) {

        Checker checker1 = checker.orElse(new Checker());
        if (checker1.isCheck()) {
            reservation.setArrived((byte) 1);
        } else {
            reservation.setArrived((byte) 0);
        }

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.POST);

        if (reservationService.findById(reservation.getId()) != null) {
            reservationService.update(reservation);
        } else {
            reservationService.add(reservation);
        }
        return new ModelAndView(redirectURL + "/?page=" + view.getPage() + "&size=" + view.getSize()
                + "&sort=" + view.getSort());
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id,
                               @RequestParam Optional<Integer> page,
                               @RequestParam Optional<Integer> size,
                               @RequestParam Optional<String> sort,
                               @RequestParam Optional<String> fio,
                               @RequestParam Optional<String> date,
                               @RequestParam Optional<String> phone,
                               HttpServletRequest request) {

        URL.IPInfo(relativeURL + "/delete/", request.getRemoteAddr(), RequestMethod.GET);
        reservationService.delete(id);

        return new ModelAndView(redirectURL + "/?page=" + page.get() + "&size=" + size.get() + "&sort="
                + sort.get() + "&fio=" + fio.orElse("") + "&date=" + date.orElse("")
                + "&phone=" + phone.orElse(""));
    }
}
