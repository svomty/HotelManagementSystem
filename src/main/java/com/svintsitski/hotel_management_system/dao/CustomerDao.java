package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.Customer;
import com.svintsitski.hotel_management_system.model.ResultQuery;

public interface CustomerDao {
    ResultQuery findAll(int start, int total, String sort) throws Exception;

    Customer findById(int id);

    void add(Customer customer);

    void update(Customer customer);

    void delete(int id);
}
