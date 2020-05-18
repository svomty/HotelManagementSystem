package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.enam.Activity;
import com.svintsitski.hotel_management_system.model.report.ReservationPdfGeneration;
import com.svintsitski.hotel_management_system.model.support.*;
import com.svintsitski.hotel_management_system.service.ApartmentService;
import com.svintsitski.hotel_management_system.service.ApartmentTypeService;
import com.svintsitski.hotel_management_system.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    private static final String DIRECTORY = "results";
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private ApartmentTypeService apartmentTypeService;
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

        //инициализировали листы
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
        ModelAndView model = new ModelAndView();
        int typeId = reservation.getApartment_id();

        ResultQuery result = apartmentService.findForDate(reservation.getArrival_date(),
                reservation.getDeparture_date(),
                Activity.Reservation,
                0);

        //инициализировали лист
        List<Apartment> apartmentList = (List<Apartment>) result.getList().get(0);
        List<ApartmentType> apartmentType = (List<ApartmentType>) result.getList().get(1);

        Apartment apartment = reservationService
                .reservationForUsers(apartmentList,
                        typeId);

        if (apartment != null) {
            reservation.setApartment_id(apartment.getId());
            int infoId = reservationService.add(reservation);
            String UUID = reservationService.findById(infoId).getUUID();
            return "redirect:/reservation/info/" + UUID;
        } else {
            return "error";
        }
    }

    @GetMapping("/reservation/info/{id}")
    public ModelAndView addReservation(HttpServletRequest request,
                                       @PathVariable("id") String id
    ) throws Exception {
        ModelAndView model = new ModelAndView();

        Reservation reservation = reservationService.findByUUID(id);

        model.addObject("config", Config.getInstance());
        model.addObject("reservation", reservation);
        model.setViewName("reservation_info");
        return model;
    }

    @RequestMapping("/report/{id}")
    public ResponseEntity<InputStreamResource> downloadFile1(
            @PathVariable("id") String UUID) throws IOException {

        Reservation reservation = reservationService.findByUUID(UUID);

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext,
                reservation.getId() + ".pdf");

        Apartment apartment = apartmentService.findById(reservation.getApartment_id());
        ApartmentType apartmentType = apartmentTypeService.findById(apartment.getType_id());

        ReservationPdfGeneration pdf = new ReservationPdfGeneration(reservation, apartment, apartmentType);

        pdf.createPdf();

        File file = new File(DIRECTORY + "/" + reservation.getId() + ".pdf");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Content-Length
                .contentLength(file.length()) //
                .body(resource);
    }
}

