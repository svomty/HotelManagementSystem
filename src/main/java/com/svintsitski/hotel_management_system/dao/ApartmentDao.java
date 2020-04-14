package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.Apartment;

import java.util.List;

public interface ApartmentDao {
    List<Apartment> findAll(String sort) throws Exception;

    Apartment findById(int id);

    List<Apartment> findByType(int id);

    int add(Apartment apartment);

    int update(Apartment apartment);

    int delete(int id);
}
