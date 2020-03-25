package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentTypeDaoImpl;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentTypeServiceImpl implements ApartmentTypeService  {

    @Autowired
    private ApartmentTypeDaoImpl apartmentDao;

    @Override
    public ResultQuery findAll(int start, int total) {
        return apartmentDao.findAll(start, total);
    }

    @Override
    public ApartmentType findById(int id) {
        //return apartmentDao.findById(id);
        return null;
    }

    @Override
    public List<ApartmentType> findByType(String type, int start, int total) {
        return apartmentDao.findByType(type, start, total);
    }

    @Override
    public void add(ApartmentType apartmentType) {
        //apartmentDao.add(apartmentType);
    }

    @Override
    public void update(ApartmentType apartmentType) {
        //apartmentDao.update(apartmentType);
    }

    @Override
    public void delete(int id) {
        //apartmentDao.delete(id);
    }
}
