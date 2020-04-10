package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.Accommodation;
import com.svintsitski.hotel_management_system.model.ResultQuery;

public interface AccommodationDao {
    ResultQuery findAll(int start, int total, String sort);

    Accommodation findById(int id);

    int add(Accommodation accommodation);

    int update(Accommodation accommodation);

    int delete(int id);
}
