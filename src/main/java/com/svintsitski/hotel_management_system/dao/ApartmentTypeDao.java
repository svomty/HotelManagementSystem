package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;

import java.util.List;

public interface ApartmentTypeDao {
    ResultQuery findAll(int start, int total, String sort);

    List<ApartmentType> findById(int id);

    List<ApartmentType> findByType(String type, int start, int total);

    void add(ApartmentType apartmentType);

    void update(ApartmentType apartmentType);

    void delete(int id);

}
