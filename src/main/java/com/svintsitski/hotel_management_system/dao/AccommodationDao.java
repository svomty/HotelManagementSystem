package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.Accommodation;

import java.util.List;

public interface AccommodationDao {
    List<Accommodation> findAll(String sort);

    Accommodation findById(int id);

    List<Accommodation>  findByApartmentId(int id);

    List<Accommodation>  findByCustomerId(int id);

    int add(Accommodation accommodation);

    int update(Accommodation accommodation);

    int delete(int id);
}
