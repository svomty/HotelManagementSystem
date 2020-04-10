package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.AccommodationDaoImpl;
import com.svintsitski.hotel_management_system.model.Accommodation;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccommodationServiceImpl implements AccommodationService {
    @Autowired
    private AccommodationDaoImpl hotelAccommodationDao;

    @Override
    public ResultQuery findAll(int start, int total, String sort) throws Exception {
        return hotelAccommodationDao.findAll(start, total, sort);
    }

    @Override
    public Accommodation findById(int id) {
        return hotelAccommodationDao.findById(id);
    }

    @Override
    public int add(Accommodation accommodation) {
        return hotelAccommodationDao.add(accommodation);
    }

    @Override
    public int update(Accommodation accommodation) {
        return hotelAccommodationDao.update(accommodation);
    }

    @Override
    public int delete(int id) {
        return hotelAccommodationDao.delete(id);
    }
}
