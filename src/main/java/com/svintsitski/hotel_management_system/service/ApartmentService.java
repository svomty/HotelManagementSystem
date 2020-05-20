package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.enam.Activity;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

import java.sql.Date;

public interface ApartmentService {
    ResultQuery findAll(int start, int total, String sort) throws Exception;

    ResultQuery findAll(String sort) throws Exception;

    ResultQuery filter(int start, int total, String sort,
                       String type, Integer place, Integer room) throws Exception;

    Apartment findById(int id);

    ResultQuery findForDate(Date arrival_date, Date departure_date, Activity activity, int id) throws Exception;

    int add(Apartment apartment);

    int update(Apartment apartment);

    int delete(int id);
}
