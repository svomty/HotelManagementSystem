package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ResultQuery;

public interface ApartmentDao {
    ResultQuery findAll(int start, int total, String sort) throws Exception;

    Apartment findById(int id);

    int add(Apartment apartment);

    int update(Apartment apartment);

    int delete(int id);
}
