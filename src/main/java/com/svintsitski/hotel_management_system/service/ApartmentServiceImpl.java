package com.svintsitski.hotel_management_system.service;

import com.svintsitski.hotel_management_system.dao.AccommodationDao;
import com.svintsitski.hotel_management_system.dao.ApartmentDao;
import com.svintsitski.hotel_management_system.dao.ApartmentTypeDao;
import com.svintsitski.hotel_management_system.dao.ReservationDao;
import com.svintsitski.hotel_management_system.model.database.Accommodation;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.enam.Activity;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentDao apartmentDao;
    @Autowired
    private ApartmentTypeDao apartmentTypeDao;
    @Autowired
    private AccommodationDao accommodationDao;
    @Autowired
    private ReservationDao reservationDao;

    @Override
    public ResultQuery findAll(int start, int total, String sort) throws Exception {

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
        int count = apartments.size();

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

        if (start > total) {
            apartmentType = new ArrayList<>();
        } else {
            apartments = new ArrayList<>(apartments.subList(start, total));
            //apartments урезали для пагинации

            apartmentType = new ArrayList<>();
            for (int i = 0; i < apartments.size(); i++) {
                apartmentType.add(apartmentTypeDao.findById(apartments.get(i).getType_id()));
            }
            //apartmentType нашли
        }

        return new ResultQuery(count, Arrays.asList(apartments, apartmentType));
    }

    @Override
    public ResultQuery findForDate(Date arrival_date, Date departure_date, Activity activity, int id) throws Exception {
        List<ApartmentType> apartmentType;
        List<Apartment> apartments;
        List<Accommodation> accommodations;
        List<Reservation> reservations;
        List<Byte> totalPlaces;

        apartments = apartmentDao.findAll("number");
        accommodations = accommodationDao.findAll("id");
        reservations = reservationDao.findAll("id");
        totalPlaces = new ArrayList<>();
        apartmentType = new ArrayList<>();
        for (int i = 0; i < apartments.size(); i++) {
            apartmentType.add(apartmentTypeDao.findById(apartments.get(i).getType_id()));
            totalPlaces.add(apartmentType.get(i).getPlaces_number());
        }
        //apartmentType нашли

        int idForReservation;

        System.out.println("reservation");
        for (Reservation reservation : reservations) {//идем по reservations
            if (//если на текущую дату апартамент забронирован - минусуем места на 1
                    reservation.getArrival_date().after(arrival_date)
                            && reservation.getArrival_date().before(departure_date)

                            ||

                            reservation.getDeparture_date().after(arrival_date)
                                    && (reservation.getDeparture_date().before(departure_date)
                                    || reservation.getDeparture_date().equals(departure_date))
            ) {
                if (reservation.getId() == id && Activity.Reservation == activity) {
                    continue;
                }

                //условие нужно для того, чтобы бронирование перешедшее в заселение не повторялось несколько раз
                if (reservation.getArrived() != 1 && Activity.Accommodation == activity) {
                    continue; //возможно это условие лишнее
                }

                for (int i = 0; i < apartments.size(); i++) { //ищем апартамент

                    if (apartments.get(i).getId() == reservation.getApartment_id()) {
                        System.out.println(reservation.getApartment_id());

                        if (reservation.getApartment_id() == id &&
                                Activity.ReservationToAccommodation == activity) {
                            break;
                        } else if (Activity.Accommodation == activity &&
                                reservation.getArrived() == 1) {
                            break;
                        } else if (Activity.Reservation == activity &&
                                reservation.getArrived() == 1) {
                            break;
                        } else {
                            apartmentType.remove(i);
                            apartments.remove(i);
                            totalPlaces.remove(i);
                        }

                        break;
                    }
                }
            }

        }

        System.out.println("accommodation");
        for (Accommodation accommodation : accommodations) {//идем по заселению
            if (//если на текущую дату апартамент занят - минусуем места на 1
                    accommodation.getArrival_date().after(arrival_date)
                            && accommodation.getArrival_date().before(departure_date)

                            ||

                            accommodation.getDeparture_date().after(arrival_date)
                                    && (accommodation.getDeparture_date().before(departure_date)
                                    || accommodation.getDeparture_date().equals(departure_date))
            ) {
                if (accommodation.getId() == id && Activity.Accommodation == activity) {
                    continue;
                }
                for (int i = 0; i < apartments.size(); i++) { //ищем апартамент

                    if (apartments.get(i).getId() == accommodation.getApartment_id()) {
                        if (Activity.Reservation == activity) {
                            apartmentType.remove(i);
                            apartments.remove(i);
                            totalPlaces.remove(i);
                            break;
                        }
                        System.out.println(accommodation.getApartment_id());
                        byte places_number = apartmentType.get(i).getPlaces_number();

                        apartmentType.get(i).setPlaces_number((byte) (places_number - 1));

                        if (apartmentType.get(i).getPlaces_number() == places_number) { //удаляем апартамент и его тип
                            System.out.println("delete " + accommodation.getApartment_id());
                            apartmentType.remove(i);
                            apartments.remove(i);
                            totalPlaces.remove(i);
                        }
                        break;
                    }
                }
            }
        }

        return new ResultQuery(apartments.size(), Arrays.asList(apartments, apartmentType, totalPlaces));
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
