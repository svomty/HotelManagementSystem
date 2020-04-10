package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.model.Accommodation;
import com.svintsitski.hotel_management_system.model.ResultQuery;

public interface AccommodationService {

    ResultQuery findAll(int start, int total, String sort) throws Exception;

    Accommodation findById(int id);

    int add(Accommodation accommodation);

    int update(Accommodation accommodation);

    int delete(int id);

}
