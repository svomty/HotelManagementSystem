package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.ForeignCustomer;

import java.util.List;

public interface ForeignCustomerDao {
    List<ForeignCustomer> findAll(String sort);

    ForeignCustomer findById(int id);

    int add(ForeignCustomer customer);

    int update(ForeignCustomer customer);

    int delete(int id);
}
