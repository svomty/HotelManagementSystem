package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.AccommodationDaoImpl;
import com.svintsitski.hotel_management_system.dao.ApartmentDao;
import com.svintsitski.hotel_management_system.dao.CustomerDao;
import com.svintsitski.hotel_management_system.model.database.Accommodation;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.Customer;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AccommodationServiceImpl implements AccommodationService {
    @Autowired
    private AccommodationDaoImpl accommodationDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ApartmentDao apartmentDao;

    @Override
    public List<List<?>> findAll(int start, int total, String sort) throws Exception {

        start = start - 1;
        total += start;

        List<Accommodation> accommodations;
        List<Customer> customers;
        List<Apartment> apartments;

        boolean sortByApartment = sort.equals("number");
        boolean sortByCustomer = sort.equals("surname");

        if (sortByApartment || sortByCustomer) {
            accommodations = accommodationDao.findAll("id");
        } else {
            accommodations = accommodationDao.findAll(sort);
        }

        if (sortByApartment) {

            apartments = apartmentDao.findAll(sort);
            //получили весь список апартаментов

            accommodations = new ArrayList<>();
            //обнулили accommodations

            for (int i = 0; i < apartments.size(); i++) {
                accommodations.addAll(accommodationDao.findByApartmentId(apartments.get(i).getId()));
            }
            //нашли апартаменты
        } else if (sortByCustomer) {
            customers = customerDao.findAll(sort);
            //получили весь список customers

            accommodations = new ArrayList<>();
            //обнулили accommodations

            for (int i = 0; i < customers.size(); i++) {
                accommodations.addAll(accommodationDao.findByCustomerId(customers.get(i).getId()));
            }
            //нашли апартаменты
        }

        if (accommodations.size() < total) {
            total = accommodations.size();
        }
        accommodations = new ArrayList<>(accommodations.subList(start, total));
        //accommodations урезали для пагинации

        customers = new ArrayList<>();
        apartments = new ArrayList<>();

        for (Accommodation accommodation : accommodations) {
            customers.add(customerDao.findById(accommodation.getCustomer_id()));
            apartments.add(apartmentDao.findById(accommodation.getApartment_id()));
        }
        //нашли customers и apartments
        return Arrays.asList(accommodations, customers, apartments);
    }

    @Override
    public Accommodation findById(int id) {
        return accommodationDao.findById(id);
    }

    @Override
    public int add(Accommodation accommodation) {
        return accommodationDao.add(accommodation);
    }

    @Override
    public int update(Accommodation accommodation) {
        return accommodationDao.update(accommodation);
    }

    @Override
    public int delete(int id) {
        return accommodationDao.delete(id);
    }
}
