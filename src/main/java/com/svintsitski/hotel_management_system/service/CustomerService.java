package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll(int start, int total, String sort) throws Exception;

    Customer findById(int id);

    int add(Customer customer);

    int update(Customer customer);

    int delete(int id);
}
