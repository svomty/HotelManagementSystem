package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;

import java.util.List;

public interface ApartmentTypeDao {
    ResultQuery findAll(int start, int total, String sort);

    ApartmentType findById(int id);

    void add(ApartmentType apartmentType);

    void update(ApartmentType apartmentType);

    void delete(int id);

}
