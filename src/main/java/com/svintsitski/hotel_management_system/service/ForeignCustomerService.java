package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.ForeignCustomer;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

import java.util.List;

public interface ForeignCustomerService {

    ResultQuery findAll(int start, int total, String sort) throws Exception;

    ForeignCustomer findById(int id);

    int add(ForeignCustomer customer);

    int update(ForeignCustomer customer);

    int delete(int id);
}
