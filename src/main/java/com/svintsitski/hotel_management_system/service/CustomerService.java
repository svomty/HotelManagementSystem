package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.Customer;
import com.svintsitski.hotel_management_system.model.ResultQuery;

public interface CustomerService {
    ResultQuery findAll(int start, int total, String sort) throws Exception;

    Customer findById(int id);

    void add(Customer customer);

    void update(Customer customer);

    void delete(int id);
}