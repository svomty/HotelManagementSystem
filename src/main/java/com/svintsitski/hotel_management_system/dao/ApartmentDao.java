package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ResultQuery;

public interface ApartmentDao {
    ResultQuery findAll(int start, int total, String sort);

    Apartment findById(int id);

    void add(Apartment apartment);

    void update(Apartment apartment);

    void delete(int id);
}
