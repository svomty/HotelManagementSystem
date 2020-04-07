package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.ForeignCustomer;
import com.svintsitski.hotel_management_system.model.ResultQuery;

public interface ForeignCustomerService {

    ResultQuery findAll(int start, int total, String sort) throws Exception;

    ForeignCustomer findById(int id);

    void add(ForeignCustomer customer);

    void update(ForeignCustomer customer);

    void delete(int id);
}
