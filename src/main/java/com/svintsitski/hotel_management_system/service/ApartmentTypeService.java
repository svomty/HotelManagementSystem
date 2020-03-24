package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ApartmentType;

import java.util.List;

public interface ApartmentTypeService {
    List<ApartmentType> getAll();

    ApartmentType findById(int id);

    void add(ApartmentType apartmentType);

    void update(ApartmentType apartmentType);

    void delete(int id);
}
