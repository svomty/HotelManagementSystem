package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentDao;
import com.svintsitski.hotel_management_system.dao.ReservationDaoImpl;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationDaoImpl reservationDao;
    @Autowired
    private ApartmentDao apartmentDao;

    @Override
    public ResultQuery findAll(int start, int total, String sort) throws Exception {

        start -= 1;
        total += start;

        List<Reservation> reservations;
        List<Apartment> apartments;

        boolean sortByApartment = sort.equals("number");

        if (sortByApartment) {
            reservations = reservationDao.findAll("id");
        } else {
            reservations = reservationDao.findAll(sort);
        }

        int count = reservations.size();

        if (sortByApartment) {

            apartments = apartmentDao.findAll(sort);
            //получили весь список апартаментов

            reservations = new ArrayList<>();
            //обнулили reservations

            for (int i = 0; i < apartments.size(); i++) {
                reservations.addAll(reservationDao.findByApartmentId(apartments.get(i).getId()));
            }
            //нашли reservations
        }

        if (reservations.size() < total) {
            total = reservations.size();
        }
        reservations = new ArrayList<>(reservations.subList(start, total));
        //reservations урезали для пагинации

        apartments = new ArrayList<>();

        for (Reservation reservation : reservations) {
            apartments.add(apartmentDao.findById(reservation.getApartment_id()));
        }
        //нашли apartments

        return new ResultQuery(count, Arrays.asList(reservations, apartments));
    }

    @Override
    public Reservation findById(int id) {
        return reservationDao.findById(id);
    }

    @Override
    public int add(Reservation reservation) {
        return reservationDao.add(reservation);
    }

    @Override
    public int update(Reservation reservation) {
        return reservationDao.update(reservation);
    }

    @Override
    public int delete(int id) {
        return reservationDao.delete(id);
    }
}
