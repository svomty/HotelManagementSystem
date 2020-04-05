package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.ServingWebContentApplication;
import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.Customer;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import com.svintsitski.hotel_management_system.service.ApartmentServiceImpl;
import com.svintsitski.hotel_management_system.service.ApartmentTypeServiceImpl;
import com.svintsitski.hotel_management_system.service.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(com.svintsitski.hotel_management_system.controller
            .CustomerController.class);

    String url;
    String ip;

    @GetMapping(value = {"/list/", "/list", "/", ""})
    public String findAll(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort, Model model, HttpServletRequest request) throws Exception {

        int current_page = page.orElse(1);
        String sorting = sort.orElse("id");
        int start_page = (current_page - 2) < 0 ? 0 : current_page - 2;
        int default_page_size = MainController.default_page_size;
        int page_size = size.orElse(default_page_size);
        page_size = (page_size < 1) ? default_page_size : page_size;
        int start = 1 + (current_page - 1) * page_size;

        ResultQuery result = customerService.findAll(start, page_size, sorting);
        int full_elem_count = result.getCount();
        List resultQueryList = result.getList();

        int total_page = (int) Math.ceil((float) full_elem_count / (float) page_size);
        total_page = Math.max(total_page, 1);

        url = ServingWebContentApplication.DOMAIN_FULL + "admin/customer/list/";
        ip = request.getRemoteAddr();
        String createURL = ServingWebContentApplication.DOMAIN_FULL + "admin/customer/add";

        LOGGER.info("[" + ip + "] requested " + url);

        model.addAttribute("customer_list", resultQueryList);
        model.addAttribute("createURL", createURL);
        model.addAttribute("current_page", current_page);
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", page_size);
        model.addAttribute("start_page", start_page);
        model.addAttribute("sort", sorting);

        return "customer";
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id, HttpServletRequest request) throws Exception {
        ModelAndView model = new ModelAndView();
        Customer customer = customerService.findById(id);
        List<Customer> customerList = customerService.findAll(1, 1000, "id").getList();

        url = ServingWebContentApplication.DOMAIN_FULL + "admin/customer/update/" + id;
        ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested " + url + ". Customer №" + customer.getId() + " will be updated");

        model.addObject("customer", customer);
        model.setViewName("customer_add");
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Customer customer = new Customer();

        url = ServingWebContentApplication.DOMAIN_FULL + "admin/customer/add/";
        ip = request.getRemoteAddr();

        LOGGER.info("[" + ip + "] requested " + url + ". Customer will be added");

        model.addObject("customer", customer);
        model.setViewName("customer_add");
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("customer") Customer customer, HttpServletRequest request) {
        url = ServingWebContentApplication.DOMAIN_FULL + "admin/customer/add/";
        ip = request.getRemoteAddr();
        if (customerService.findById(customer.getId()) != null) {
            customerService.update(customer);
            LOGGER.info("[" + ip + "] requested " + url + ". Customer №" + customer.getId() + " was updated");
        } else {
            customerService.add(customer);
            LOGGER.info("[" + ip + "] requested " + url + ". Customer " + customer.getSurname() + " " +
                    customer.getName() + " was created");
        }
        return new ModelAndView("redirect:/admin/customer/");
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id, HttpServletRequest request) {
        url = ServingWebContentApplication.DOMAIN_FULL + "admin/customer/delete/" + id;
        ip = request.getRemoteAddr();

        customerService.delete(id);

        LOGGER.info("[" + ip + "] requested " + url + ". Customer №" + id + " was deleted");
        return new ModelAndView("redirect:/admin/customer/");
    }
}
