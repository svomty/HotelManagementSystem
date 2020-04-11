package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

public interface ApartmentService {
    ResultQuery findAll(int start, int total, String sort);

    Apartment findById(int id);

    int add(Apartment apartment);

    int update(Apartment apartment);

    int delete(int id);
}
