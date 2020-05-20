package com.svintsitski.hotel_management_system.controller;

import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Customer;
import com.svintsitski.hotel_management_system.model.database.ForeignCustomer;
import com.svintsitski.hotel_management_system.model.support.*;
import com.svintsitski.hotel_management_system.service.CustomerServiceImpl;
import com.svintsitski.hotel_management_system.service.ForeignCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    @Autowired
    private ForeignCustomerServiceImpl foreignCustomerService;

    String mainObject = "customer";
    String relativeURL = "admin/" + mainObject;
    String redirectURL = "redirect:/" + relativeURL;
    String jspAdd = relativeURL + "_add";

    @GetMapping(value = {"/list/", "/list", "/", ""})
    public String findAll(@RequestParam Optional<Integer> page,
                          @RequestParam Optional<Integer> size,
                          @RequestParam Optional<String> sort,
                          @RequestParam Optional<String> fio,
                          @RequestParam Optional<String> password,
                          Model model,
                          HttpServletRequest request) throws Exception {

        String sorting = sort.orElse("id");
        Pagination pagination = new Pagination(page.orElse(1), size.orElse(Config.getInstance().getCountElem()));

        ResultQuery result = foreignCustomerService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);

        List<Customer> customerList = (List<Customer>) result.getList().get(0);

        //удаление
        if (customerList.size() == 0) {
            pagination = new Pagination(pagination.getTotalPage(result.getCount()), size.orElse(Config.getInstance().getCountElem()));

            result = foreignCustomerService.findAll(pagination.getStartElem(), pagination.getPage_size(), sorting);

            customerList = (List<Customer>) result.getList().get(0);
        }
        //удаление
//фильтрация
        if (password.isPresent() || fio.isPresent()) {

            result = foreignCustomerService.filter(pagination.getStartElem(), pagination.getPage_size(), sorting,
                    fio.get(), password.get());

            customerList = (List<Customer>) result.getList().get(0);
        }
//фильтрация
        int full_elem_count = result.getCount();
        List<ForeignCustomer> foreignCustomerList = (List<ForeignCustomer>) result.getList().get(1);

        int total_page = pagination.getTotalPage(full_elem_count);

        URL.IPInfo(relativeURL + "/list/", request.getRemoteAddr(), RequestMethod.GET);

        model.addAttribute("view", new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sorting));
        model.addAttribute("customer_list", customerList);
        model.addAttribute("foreign_customer_list", foreignCustomerList);
        model.addAttribute("current_page", pagination.getCurrent_page());
        model.addAttribute("total_page", total_page);
        model.addAttribute("size", pagination.getPage_size());
        model.addAttribute("start_page", pagination.getStart_page());
        model.addAttribute("sort", sorting);
        model.addAttribute("fio", fio.orElse(""));
        model.addAttribute("password", password.orElse(""));
        model.addAttribute("config", Config.getInstance());
        return relativeURL;
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable int id,
                             @RequestParam Optional<Integer> page,
                             @RequestParam Optional<Integer> size,
                             @RequestParam Optional<String> sort,
                             HttpServletRequest request) throws Exception {
        ModelAndView model = new ModelAndView();
        Customer customer = customerService.findById(id);
        ForeignCustomer foreignCustomer = foreignCustomerService.findById(id);
        Checker checker = new Checker(foreignCustomer.getCustomer_id() != 0);

        URL.IPInfo(relativeURL + "/update/", request.getRemoteAddr(), RequestMethod.GET);

        View view = new View(page.orElse(1), size.orElse(Config.getInstance().getCountElem()), sort.orElse("id"));
        model.addObject("view", view);

        model.addObject("customer", customer);
        model.addObject("checker", checker);
        model.addObject("foreignCustomer", foreignCustomer);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @GetMapping(value = {"/add/", "/add"})
    public ModelAndView add(HttpServletRequest request,
                            @ModelAttribute("view") View view) {
        ModelAndView model = new ModelAndView();

        Customer customer = new Customer();
        ForeignCustomer foreignCustomer = new ForeignCustomer();
        Checker checker = new Checker();

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.GET);

        model.addObject("view", view);
        model.addObject("customer", customer);
        model.addObject("checker", checker);
        model.addObject("foreignCustomer", foreignCustomer);
        model.addObject("config", Config.getInstance());
        model.setViewName(jspAdd);
        return model;
    }

    @PostMapping(value = {"/add/", "/add"})
    public ModelAndView save(@ModelAttribute("customer") Customer customer,
                             @ModelAttribute("foreignCustomer") Optional<ForeignCustomer> foreignCustomer,
                             BindingResult bindingResult1,
                             @ModelAttribute("checker") Optional<Checker> checker,
                             BindingResult bindingResult,
                             @ModelAttribute("view") View view,
                             HttpServletRequest request) {

        URL.IPInfo(relativeURL + "/add/", request.getRemoteAddr(), RequestMethod.POST);
        Checker checker1 = checker.orElse(new Checker());

        if (customerService.findById(customer.getId()) != null) {

            customerService.update(customer);

            if (checker1.isCheck()) {
                ForeignCustomer newForeignCustomer = foreignCustomer.orElse(new ForeignCustomer());
                newForeignCustomer.setCustomer_id(customer.getId());

                if (foreignCustomerService.findById(customer.getId()).getCustomer_id() != 0) {
                    foreignCustomerService.update(newForeignCustomer);
                } else {
                    foreignCustomerService.add(newForeignCustomer);
                }

            } else {
                foreignCustomerService.delete(customer.getId());
            }

        } else {
            int id = customerService.add(customer);
            if (checker1.isCheck()) {
                ForeignCustomer newForeignCustomer = foreignCustomer.orElse(new ForeignCustomer());
                newForeignCustomer.setCustomer_id(id);
                foreignCustomerService.add(newForeignCustomer);
            }
        }

        bindingResult1.hasErrors();
        bindingResult.hasErrors();

        return new ModelAndView(redirectURL + "/?page=" + view.getPage() + "&size=" + view.getSize()
                + "&sort=" + view.getSort());
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id,
                               @RequestParam Optional<Integer> page,
                               @RequestParam Optional<Integer> size,
                               @RequestParam Optional<String> sort,
                               @RequestParam Optional<String> fio,
                               @RequestParam Optional<String> password,
                               HttpServletRequest request) {

        URL.IPInfo(relativeURL + "/delete/", request.getRemoteAddr(), RequestMethod.GET);

        foreignCustomerService.delete(id);
        customerService.delete(id);

        return new ModelAndView(redirectURL + "?page=" + page.get() + "&size=" + size.get()
                + "&sort=" + sort.get() + "&fio=" + fio.orElse("") + "&password=" + password.orElse(""));
    }
}
