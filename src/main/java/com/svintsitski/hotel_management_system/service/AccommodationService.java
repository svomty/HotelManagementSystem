package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.Accommodation;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

import java.sql.Date;

public interface AccommodationService {

    ResultQuery findAll(int start, int total, String sort) throws Exception;

    ResultQuery findAll(String sort) throws Exception;

    ResultQuery findForDate(Date arrival_date, Date departure_date) throws Exception;

    ResultQuery filter(int start, int total, String sort,
                       String fio, String date, Integer apart) throws Exception;

    ResultQuery searchForCurrentDate(int start,
                                     int total,
                                     String sort) throws Exception;

    Accommodation findById(int id);

    int add(Accommodation accommodation);

    int update(Accommodation accommodation);

    int delete(int id);

}
