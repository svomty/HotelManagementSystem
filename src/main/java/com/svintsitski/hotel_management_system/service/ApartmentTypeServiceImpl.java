package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentTypeDaoImpl;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApartmentTypeServiceImpl implements ApartmentTypeService {

    @Autowired
    private ApartmentTypeDaoImpl apartmentDao;

    @Override
    public ResultQuery findAll(int start, int total, String sort) {
        return apartmentDao.findAll(start, total, sort);
    }

    @Override
    public ApartmentType findById(int id) {
        return apartmentDao.findById(id);
    }

    @Override
    public void add(ApartmentType apartmentType) {
        apartmentDao.add(apartmentType);
    }

    @Override
    public void update(ApartmentType apartmentType) {
        apartmentDao.update(apartmentType);
    }

    @Override
    public void delete(int id) {
        apartmentDao.delete(id);
    }
}
