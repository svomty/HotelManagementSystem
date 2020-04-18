package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.Reservation;

import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll(String sort);

    Reservation findById(int id);

    List<Reservation> findByApartmentId(int id);

    int add(Reservation reservation);

    int update(Reservation reservation);

    int delete(int id);
}
