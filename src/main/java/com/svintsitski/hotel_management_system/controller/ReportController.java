package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.*;
import com.svintsitski.hotel_management_system.model.report.ExcelReportsGenerator;
import com.svintsitski.hotel_management_system.model.report.ReservationPdfGeneration;
import com.svintsitski.hotel_management_system.model.support.MediaTypeUtils;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import com.svintsitski.hotel_management_system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportController {

    private static final String DIRECTORY = "results";

    @Autowired
    private AccommodationServiceImpl accommodationService;
    @Autowired
    private ApartmentTypeService apartmentTypeService;
    @Autowired
    private ForeignCustomerServiceImpl foreignCustomerService;
    @Autowired
    private ReservationServiceImpl reservationService;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private ServletContext servletContext;

    @GetMapping("/reports/reservation_report/info/{id}")
    public ModelAndView addReservation(@PathVariable("id") String id) {
        ModelAndView model = new ModelAndView();

        Reservation reservation = reservationService.findByUUID(id);

        model.addObject("config", Config.getInstance());
        model.addObject("reservation", reservation);
        model.setViewName("reservation_info");
        return model;
    }

    @GetMapping("/reports/reservation_report/{id}")
    public ResponseEntity<InputStreamResource> getReservationReport(@PathVariable("id") String UUID) throws IOException {

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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping(value = "/admin/reports/stay_report")
    public ModelAndView stay_report() {
        ModelAndView model = new ModelAndView();
        model.addObject("reservation", new Reservation());
        model.addObject("config", Config.getInstance());
        model.setViewName("/admin/reports/stay_report");
        return model;
    }

    @PostMapping(value = "/admin/reports/stay_report")
    public ModelAndView download_stay_report(@ModelAttribute("reservation") Reservation reservation) throws Exception {

        ResultQuery resultQuery = accommodationService.findForDate(reservation.getArrival_date(), reservation.getDeparture_date());
        List<Accommodation> accommodations = (List<Accommodation>) resultQuery.getList().get(0);
        List<Customer> customers = (List<Customer>) resultQuery.getList().get(1);
        List<Apartment> apartments = (List<Apartment>) resultQuery.getList().get(2);
        List<ApartmentType> apartmentTypes = new ArrayList<>();

        for (Apartment apartment : apartments) {
            apartmentTypes.add(apartmentTypeService.findById(apartment.getType_id()));
        }

        ModelAndView modelAndView = new ModelAndView(new ExcelReportsGenerator(), "applications", reservation);
        modelAndView.addObject("accommodations", accommodations);
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("apartments", apartments);
        modelAndView.addObject("apartmentTypes", apartmentTypes);
        modelAndView.addObject("report_type", "stay_report");
        return modelAndView;
    }

    @GetMapping(value = "/admin/reports/foreign_stay_report")
    public ModelAndView foreign_stay_report() throws Exception {
        ModelAndView model = new ModelAndView();
        model.addObject("reservation", new Reservation());
        model.addObject("config", Config.getInstance());
        model.setViewName("/admin/reports/foreign_stay_report");
        return model;
    }

    @PostMapping(value = "/admin/reports/foreign_stay_report")
    public ModelAndView download_foreign_stay_report(@ModelAttribute("reservation")
                                                             Reservation reservation) throws Exception {

        ResultQuery resultQuery = accommodationService.findForDate(reservation.getArrival_date(), reservation.getDeparture_date());
        List<Accommodation> accommodations = (List<Accommodation>) resultQuery.getList().get(0);
        List<Customer> customers = (List<Customer>) resultQuery.getList().get(1);
        List<Apartment> apartments = (List<Apartment>) resultQuery.getList().get(2);
        List<ApartmentType> apartmentTypes = new ArrayList<>();
        List<ForeignCustomer> foreignCustomers = new ArrayList<>();

        for (int i = 0; i < accommodations.size(); i++) {
            apartmentTypes.add(apartmentTypeService.findById(apartments.get(i).getType_id()));
            foreignCustomers.add(foreignCustomerService.findById(customers.get(i).getId()));
        }

        ModelAndView modelAndView = new ModelAndView(new ExcelReportsGenerator(), "applications", reservation);
        modelAndView.addObject("accommodations", accommodations);
        modelAndView.addObject("foreignCustomers", foreignCustomers);
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("apartments", apartments);
        modelAndView.addObject("apartmentTypes", apartmentTypes);
        modelAndView.addObject("report_type", "foreign_stay_report");
        return modelAndView;
    }
}
