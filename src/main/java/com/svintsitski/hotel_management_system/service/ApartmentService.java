package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

import java.sql.Date;
import java.util.List;

public interface ApartmentService {
    ResultQuery findAll(int start, int total, String sort) throws Exception;

    Apartment findById(int id);

    ResultQuery findForDate(Date arrival_date, Date departure_date) throws Exception;

    List<Apartment> findByType(int id);

    int add(Apartment apartment);

    int update(Apartment apartment);

    int delete(int id);
}
