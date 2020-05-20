package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

public interface ApartmentTypeService {
    ResultQuery findAll(int start, int total, String sort);

    ResultQuery findAll(String sort) throws Exception;

    ResultQuery filter(int start, int total, String sort,
                       String type, Integer place, Integer room) throws Exception;

    ApartmentType findById(int id);

    int add(ApartmentType apartmentType);

    int update(ApartmentType apartmentType);

    int delete(int id);
}
