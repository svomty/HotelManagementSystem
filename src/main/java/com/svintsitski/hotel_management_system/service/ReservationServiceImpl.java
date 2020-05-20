package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.ApartmentDao;
import com.svintsitski.hotel_management_system.dao.ReservationDaoImpl;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        apartments = new ArrayList<>();

        if (start > total) {
            reservations = new ArrayList<>();
        } else {
            reservations = new ArrayList<>(reservations.subList(start, total));
            //reservations урезали для пагинации

            for (Reservation reservation : reservations) {
                apartments.add(apartmentDao.findById(reservation.getApartment_id()));
            }
            //нашли apartments
        }

        return new ResultQuery(count, Arrays.asList(reservations, apartments));
    }

    @Override
    public ResultQuery findAll(String sort) throws Exception {

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
    public ResultQuery filter(int start, int total, String sort,
                              String fio, String date, String phone
    ) throws Exception {

        start -= 1;
        total += start;

        ResultQuery resultQuery = findAll(sort);

        List<Reservation> reservations = (List<Reservation>) resultQuery.getList().get(0);
        List<Apartment> apartments = (List<Apartment>) resultQuery.getList().get(1);

        for (int k = 0; k < reservations.size(); k++) {
            if (!fio.equals("")) {
                if (reservations.get(k).getFull_name().toLowerCase().lastIndexOf(fio.toLowerCase()) == -1) {
                    reservations.remove(k);
                    apartments.remove(k);
                    k--;
                    continue;
                }
            }
            if (!date.equals("")) {
                if (reservations.get(k).getArrival_date().toString().toLowerCase().lastIndexOf(date.toLowerCase()) == -1) {
                    reservations.remove(k);
                    apartments.remove(k);
                    k--;
                    continue;
                }
            }
            if (!phone.equals("")) {
                if (reservations.get(k).getCustomer_phone().toLowerCase().lastIndexOf(phone.toLowerCase()) == -1) {
                    reservations.remove(k);
                    apartments.remove(k);
                    k--;
                    continue;
                }
            }
        }

        int count = reservations.size();

        if (reservations.size() < total) {
            total = reservations.size();
        }

        if (start <= total) {
            reservations = new ArrayList<>(reservations.subList(start, total));
            apartments = new ArrayList<>(apartments.subList(start, total));
        }

        return new ResultQuery(count, Arrays.asList(reservations, apartments));
    }

    @Override
    public Reservation findByUUID(String UUID) {
        return reservationDao.findByUUID(UUID);
    }

    @Override
    public ResultQuery findFreeForUsers(int start,
                                        int total,
                                        List<Apartment> apartmentList,
                                        List<ApartmentType> apartmentTypes) {

        start -= 1;
        total += start;

        List<Integer> apartmentListCounter = new ArrayList<>();
        List<ApartmentType> apartmentTypesFree = new ArrayList<>();

        for (int i = 0; i < apartmentList.size(); i++) {
            int id = apartmentTypes.get(i).getId();

            Optional<ApartmentType> r = apartmentTypesFree
                    .stream()
                    .filter(m -> id == m.getId())
                    .findFirst();

            if (!r.isPresent()) {
                apartmentListCounter.add(1);
                apartmentTypesFree.add(apartmentTypes.get(i));
            } else {
                for (int j = 0; j < apartmentTypesFree.size(); j++) {
                    if (apartmentTypesFree.get(j).getId() == r.get().getId()) {
                        apartmentListCounter.set(j, apartmentListCounter.get(j) + 1);
                        break;
                    }
                }
            }
        }

        int size = apartmentTypesFree.size();
        int counterTotal = apartmentListCounter.stream().reduce(0, Integer::sum);

        if (apartmentTypesFree.size() < total) {
            total = apartmentTypesFree.size();
        }

        apartmentTypesFree = new ArrayList<>(apartmentTypesFree.subList(start, total));
        apartmentListCounter = new ArrayList<>(apartmentListCounter.subList(start, total));

        return new ResultQuery(size, Arrays.asList(apartmentTypesFree, apartmentListCounter, counterTotal));
    }

    @Override
    public Apartment reservationForUsers(List<Apartment> apartmentList,
                                         int typeId) {
        Apartment apartment = null;

        for (Apartment value : apartmentList) {
            if (value.getType_id() == typeId) {
                apartment = value;
                break;
            }
        }

        return apartment;
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
