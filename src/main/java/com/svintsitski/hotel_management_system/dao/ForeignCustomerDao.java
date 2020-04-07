package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.ForeignCustomer;

import java.util.List;

public interface ForeignCustomerDao {
    List<ForeignCustomer> findAll(int start, int total, String sort);

    ForeignCustomer findById(int id);

    void add(ForeignCustomer customer);

    void update(ForeignCustomer customer);

    void delete(int id);
}
