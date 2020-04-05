package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.Customer;
import com.svintsitski.hotel_management_system.model.ForeignCustomer;
import com.svintsitski.hotel_management_system.model.ResultQuery;

public interface ForeignCustomerDao {
    ForeignCustomer findById(int id);

    void add(ForeignCustomer customer);

    void update(ForeignCustomer customer);

    void delete(int id);
}
