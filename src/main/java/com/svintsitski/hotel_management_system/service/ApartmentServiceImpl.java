package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentDaoImpl;
import com.svintsitski.hotel_management_system.dao.ApartmentTypeDaoImpl;
import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentDaoImpl apartmentDao;
    @Autowired
    private ApartmentTypeDaoImpl apartmentTypeDao;

    @Override
    public ResultQuery findAll(int start, int total, String sort) {
        List<Apartment> apartments = apartmentDao.findAll(start, total, sort).getList();
        List<ApartmentType> apartmentType = new ArrayList<>();
        apartments.forEach(x -> apartmentType.add(apartmentTypeDao.findById(x.getType_id())));
        return new ResultQuery(apartmentDao.findAll(start, total, sort).getCount(),
                Arrays.asList(apartments, apartmentType));
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