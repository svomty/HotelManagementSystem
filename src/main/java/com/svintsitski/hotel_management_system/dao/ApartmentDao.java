package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

public interface ApartmentDao {
    ResultQuery findAll(int start, int total, String sort) throws Exception;

    Apartment findById(int id);

    int add(Apartment apartment);

    int update(Apartment apartment);

    int delete(int id);
}
