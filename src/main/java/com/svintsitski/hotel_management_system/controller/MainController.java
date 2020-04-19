package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.support.Pagination;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import com.svintsitski.hotel_management_system.model.support.URL;
import com.svintsitski.hotel_management_system.service.ApartmentService;
import com.svintsitski.hotel_management_system.service.ApartmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
                              Model model) throws Exception {

        String sorting = "id";
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        ResultQuery result = apartmentService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);
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

        //рассчитали количество номеров с этим типом
        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo("", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("apartment_list", apartmentTypesFree);
        model.addAttribute("counter", apartmentListCounter);

        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());

        model.addAttribute("config", Config.getInstance());
        return "reservation";
    }
}