package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.AccommodationDaoImpl;
import com.svintsitski.hotel_management_system.dao.CustomerDaoImpl;
import com.svintsitski.hotel_management_system.model.*;
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
    private CustomerService customerService;
    @Autowired
    private ApartmentService apartmentService;

    @Override
    public ResultQuery findAll(int start, int total, String sort) throws Exception {

        List<Accommodation> accommodations = accommodationDao.findAll(start, total, sort).getList();
        List<Customer> customers = new ArrayList<>();
        List<Apartment> apartments = new ArrayList<>();

        for (Accommodation accommodation : accommodations) {
            customers.add(customerService.findById(accommodation.getCustomer_id()));
            apartments.add(apartmentService.findById(accommodation.getApartment_id()));
        }

        return new ResultQuery(accommodationDao.findAll(start, total, sort).getCount(),
                Arrays.asList(accommodations, customers, apartments));

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
