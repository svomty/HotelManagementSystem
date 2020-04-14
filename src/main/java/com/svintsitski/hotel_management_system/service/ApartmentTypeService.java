package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.ApartmentType;

import java.util.List;

public interface ApartmentTypeService {
    List<ApartmentType> findAll(int start, int total, String sort);

    ApartmentType findById(int id);

    int add(ApartmentType apartmentType);

    int update(ApartmentType apartmentType);

    int delete(int id);
}
