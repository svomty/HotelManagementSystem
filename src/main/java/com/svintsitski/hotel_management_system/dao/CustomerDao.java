package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.Customer;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

public interface CustomerDao {
    ResultQuery findAll(int start, int total, String sort) throws Exception;

    Customer findById(int id);

    int add(Customer customer);

    int update(Customer customer);

    int delete(int id);
}
