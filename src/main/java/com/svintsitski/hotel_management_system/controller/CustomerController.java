package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.*;
import com.svintsitski.hotel_management_system.service.CustomerServiceImpl;
import com.svintsitski.hotel_management_system.service.ForeignCustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.svintsitski.hotel_management_system.controller.MainController.default_page_size;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private ForeignCustomerServiceImpl foreignCustomerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(com.svintsitski.hotel_management_system.controller
            .CustomerController.class);

    String relativeURL = "admin/customer/";
    String redirectURL = "redirect:/" + relativeURL;

    @GetMapping(value = {"/list/", "/list", "/", ""})
    public String findAll(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort, Model model, HttpServletRequest request) throws Exception {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(default_page_size));

        ResultQuery result = foreignCustomerService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);
        int full_elem_count = result.getCount();
        List<Customer> customerList = (List<Customer>) result.getList().get(0);
        List<ForeignCustomer> foreignCustomerList = (List<ForeignCustomer>) result.getList().get(1);
        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo(relativeURL + "list/", request.getRemoteAddr(), RequestMethod.GET);
        String createURL = URL.generateURL(relativeURL + "add");

        model.addAttribute("customer_list", customerList);
        model.addAttribute("foreign_customer_list", foreignCustomerList);
        model.addAttribute("createURL", createURL);
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());
        model.addAttribute("sort", sorting);

        return "customer";
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id, HttpServletRequest request) throws Exception {
        ModelAndView model = new ModelAndView();
        Customer customer = customerService.findById(id);
        ForeignCustomer foreignCustomer = foreignCustomerService.findById(id);
        Checker checker = new Checker(foreignCustomer.getCustomer_id() != 0);

        URL.IPInfo(relativeURL + "update/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject("customer", customer);
        model.addObject("checker", checker);
        model.addObject("foreignCustomer", foreignCustomer);
        model.setViewName("customer_add");
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        Customer customer = new Customer();
        ForeignCustomer foreignCustomer = new ForeignCustomer();
        Checker checker = new Checker();

        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject("customer", customer);
        model.addObject("checker", checker);
        model.addObject("foreignCustomer", foreignCustomer);
        model.setViewName("customer_add");
        return model;
    }

    /*
   foreignCustomer не используется
     */

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("customer") Customer customer, @ModelAttribute("foreignCustomer")
            Optional<ForeignCustomer> foreignCustomer, @ModelAttribute("checker")
                                     Optional<Boolean> checker, BindingResult bindingResult, HttpServletRequest request) {
        URL.IPInfo(relativeURL + "add/", request.getRemoteAddr(), RequestMethod.POST);
        if (customerService.findById(customer.getId()) != null) {
            customerService.update(customer);
        } else {
            customerService.add(customer);
        }
        if (bindingResult.hasErrors()) {
            LOGGER.error("aaaaaa");
        }

        /*
        здесь должно быть какое то условие, определяющее стоит ли записывать клиента как иностранца или нет

        как-то удалить клеймо иностранец, если оно убрано
        * */
        /*
        if (foreignCustomer.isPresent()) {
            ForeignCustomer customer1 = foreignCustomer.get();
            if (customer1.getCitizenship() != null) {
                if (foreignCustomerService.findById(customer1.getCustomer_id()) != null) {
                    foreignCustomerService.update(customer1);
                } else {
                    foreignCustomerService.add(customer1);
                }
            }
        }
*/
        return new ModelAndView(redirectURL);
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id, HttpServletRequest request) {

        URL.IPInfo(relativeURL + "delete/", request.getRemoteAddr(), RequestMethod.GET);
        customerService.delete(id);
        foreignCustomerService.delete(id);

        return new ModelAndView(redirectURL);
    }
}
