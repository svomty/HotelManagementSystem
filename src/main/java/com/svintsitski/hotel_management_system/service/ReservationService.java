package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

public interface ReservationService {
    ResultQuery findAll(int start, int total, String sort) throws Exception;

    Reservation findById(int id);

    int add(Reservation reservation);

    int update(Reservation reservation);

    int delete(int id);
}
