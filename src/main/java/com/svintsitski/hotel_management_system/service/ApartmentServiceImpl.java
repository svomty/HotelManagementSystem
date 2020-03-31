package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentDaoImpl;
import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentDaoImpl apartmentDao;

    @Override
    public ResultQuery findAll(int start, int total, String sort) {
        return apartmentDao.findAll(start, total, sort);
    }

    @Override
    public Apartment findById(int id) {
        return apartmentDao.findById(id);
    }

    @Override
    public void add(Apartment apartment) {
        apartmentDao.add(apartment);
    }

    @Override
    public void update(Apartment apartment) {
        apartmentDao.update(apartment);
    }

    @Override
    public void delete(int id) {
        apartmentDao.delete(id);
    }
}
