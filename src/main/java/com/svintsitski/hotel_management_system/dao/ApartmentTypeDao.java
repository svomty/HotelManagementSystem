package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

public interface ApartmentTypeDao {
    ResultQuery findAll(int start, int total, String sort);

    ApartmentType findById(int id);

    int add(ApartmentType apartmentType);

    int update(ApartmentType apartmentType);

    int delete(int id);

}
