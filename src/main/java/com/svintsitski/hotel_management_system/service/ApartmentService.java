package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ResultQuery;

public interface ApartmentService {
    ResultQuery findAll(int start, int total, String sort);

    Apartment findById(int id);

    void add(Apartment apartment);

    void update(Apartment apartment);

    void delete(int id);
}
