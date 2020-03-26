package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;

import java.util.List;

public interface ApartmentTypeService {
    ResultQuery findAll(int start, int total, String sort);

    void add(ApartmentType apartmentType);

    void update(ApartmentType apartmentType);

    void delete(int id);
}
