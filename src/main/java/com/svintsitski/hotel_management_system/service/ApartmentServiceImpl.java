package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentDaoImpl;
import com.svintsitski.hotel_management_system.dao.ApartmentTypeDaoImpl;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
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
    public List<List<?>> findAll(int start, int total, String sort) {

        start = start - 1;
        total += start;

        List<ApartmentType> apartmentType;
        List<Apartment> apartments;

        boolean sortByType = sort.equals("price") || sort.equals("rooms_number") || sort.equals("places_number")
                || sort.equals("type") || sort.equals("description");

        if (sortByType) {
            apartments = apartmentDao.findAll("id");
        } else {
            apartments = apartmentDao.findAll(sort);
        }

        if (sortByType) {

            apartmentType = apartmentTypeDao.findAll(sort);
            //получили весь список типов апартаментов

            apartments = new ArrayList<>();
            //обнулили apartments

            for (int i = 0; i < apartmentType.size(); i++) {
                apartments.addAll(apartmentDao.findByType(apartmentType.get(i).getId()));
            }
            //нашли апартаменты
        }

        if (apartments.size() < total) {
            total = apartments.size();
        }
        apartments = new ArrayList<>(apartments.subList(start, total));
        //apartments урезали для пагинации

        apartmentType = new ArrayList<>();
        for (int i = 0; i < apartments.size(); i++) {
            apartmentType.add(apartmentTypeDao.findById(apartments.get(i).getType_id()));
        }
        //apartmentType нашли

        return Arrays.asList(apartments, apartmentType);
    }

    @Override
    public Apartment findById(int id) {
        return apartmentDao.findById(id);
    }

    @Override
    public List<Apartment> findByType(int id) {
        return apartmentDao.findByType(id);
    }

    @Override
    public int add(Apartment apartment) {
        return apartmentDao.add(apartment);
    }

    @Override
    public int update(Apartment apartment) {
        return apartmentDao.update(apartment);
    }

    @Override
    public int delete(int id) {
        return apartmentDao.delete(id);
    }
}
