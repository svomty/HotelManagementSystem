package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll(String sort) throws Exception;

    Customer findById(int id);

    int add(Customer customer);

    int update(Customer customer);

    int delete(int id);
}
