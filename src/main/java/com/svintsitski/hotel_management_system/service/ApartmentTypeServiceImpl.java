package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentTypeDaoImpl;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentTypeServiceImpl implements ApartmentTypeService  {

    @Autowired
    private ApartmentTypeDaoImpl apartmentDao;

    @Override
    public List<ApartmentType> findAll() {
        return apartmentDao.findAll();
    }

    @Override
    public ApartmentType findById(int id) {
        //return apartmentDao.findById(id);
        return null;
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
