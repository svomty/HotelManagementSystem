package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentTypeDaoImpl;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApartmentTypeServiceImpl implements ApartmentTypeService {

    @Autowired
    private ApartmentTypeDaoImpl apartmentDao;

    @Override
    public ResultQuery findAll(int start, int total, String sort) {
        start = start - 1;
        total += start;

        List<ApartmentType> apartments = apartmentDao.findAll(sort);
        int count = apartments.size();

        if (apartments.size() < total) {
            total = apartments.size();
        }

        if (start <= total) {
            apartments = new ArrayList<>(apartments.subList(start, total));
        }

        return new ResultQuery(count, apartments);
    }

    @Override
    public ResultQuery findAll(String sort) throws Exception {

        List<ApartmentType> apartments = apartmentDao.findAll(sort);
        int count = apartments.size();

        return new ResultQuery(count, apartments);
    }

    @Override
    public ResultQuery filter(int start, int total, String sort, String type, Integer place, Integer room) throws Exception {

        start -= 1;
        total += start;

        ResultQuery resultQuery = findAll(sort);

        List<ApartmentType> apartments = (List<ApartmentType>) resultQuery.getList();

        for (int k = 0; k < apartments.size(); k++) {
            if (!type.equals("")) {
                if (apartments.get(k).getType().toLowerCase().lastIndexOf(type.toLowerCase()) == -1) {
                    apartments.remove(k);
                    k--;
                    continue;
                }
            }
            if (place != null) {
                if (apartments.get(k).getPlaces_number() != place) {
                    apartments.remove(k);
                    k--;
                    continue;
                }
            }
            if (room != null) {
                if (apartments.get(k).getRooms_number() != room) {
                    apartments.remove(k);
                    k--;
                    continue;
                }
            }
        }

        int count = apartments.size();

        if (apartments.size() < total) {
            total = apartments.size();
        }

        if (start <= total) {
            apartments = new ArrayList<>(apartments.subList(start, total));
        }

        return new ResultQuery(count, apartments);
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
