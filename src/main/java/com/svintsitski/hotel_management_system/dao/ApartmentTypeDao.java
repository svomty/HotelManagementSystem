package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.ApartmentType;

import java.util.List;

public interface ApartmentTypeDao {
    List<ApartmentType> findAll();

    ApartmentType findById(int id);

    void add(ApartmentType apartmentType);

    void update(ApartmentType apartmentType);

    void delete(int id);
}
