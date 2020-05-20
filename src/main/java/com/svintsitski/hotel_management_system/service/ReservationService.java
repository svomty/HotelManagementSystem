package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

import java.util.List;

public interface ReservationService {
    ResultQuery findAll(int start, int total, String sort) throws Exception;

    ResultQuery findAll(String sort) throws Exception;

    ResultQuery filter(int start, int total, String sort,
                       String fio, String date, String phone) throws Exception;

    Reservation findById(int id);

    Reservation findByUUID(String UUID);

    ResultQuery findFreeForUsers(int start,
                                 int total,
                                 List<Apartment> apartmentList,
                                 List<ApartmentType> apartmentTypes);

    Apartment reservationForUsers(List<Apartment> apartmentList,
                                  int typeId);

    int add(Reservation reservation);

    int update(Reservation reservation);

    int delete(int id);
}
