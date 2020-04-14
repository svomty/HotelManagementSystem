package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.ApartmentType;

import java.util.List;

public interface ApartmentTypeDao {
    List<ApartmentType> findAll(String sort);

    ApartmentType findById(int id);

    int add(ApartmentType apartmentType);

    int update(ApartmentType apartmentType);

    int delete(int id);

}
