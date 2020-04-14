package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.database.Accommodation;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;

import java.util.List;

public interface AccommodationService {

    List<List<?>> findAll(int start, int total, String sort) throws Exception;

    Accommodation findById(int id);

    int add(Accommodation accommodation);

    int update(Accommodation accommodation);

    int delete(int id);

}
