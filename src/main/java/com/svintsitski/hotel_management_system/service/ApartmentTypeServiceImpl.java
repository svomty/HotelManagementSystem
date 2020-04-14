package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentTypeDaoImpl;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentTypeServiceImpl implements ApartmentTypeService {

    @Autowired
    private ApartmentTypeDaoImpl apartmentDao;

    @Override
    public List<ApartmentType> findAll(int start, int total, String sort) {
        return apartmentDao.findAll(start, total, sort);
    }

    @Override
    public ApartmentType findById(int id) {
        return apartmentDao.findById(id);
    }

    @Override
    public int add(ApartmentType apartmentType) {
        return apartmentDao.add(apartmentType);
    }

    @Override
    public int update(ApartmentType apartmentType) {
        return apartmentDao.update(apartmentType);
    }

    @Override
    public int delete(int id) {
        return apartmentDao.delete(id);
    }
}
