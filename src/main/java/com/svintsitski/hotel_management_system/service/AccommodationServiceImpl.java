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

import java.sql.Date;
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
    public ResultQuery findAll(int start, int total, String sort) throws Exception {

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

        int count = accommodations.size();

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

        if (start > total) {
            customers = new ArrayList<>();
            apartments = new ArrayList<>();
        } else {
            accommodations = new ArrayList<>(accommodations.subList(start, total));
            //accommodations урезали для пагинации

            customers = new ArrayList<>();
            apartments = new ArrayList<>();

            for (Accommodation accommodation : accommodations) {
                customers.add(customerDao.findById(accommodation.getCustomer_id()));
                apartments.add(apartmentDao.findById(accommodation.getApartment_id()));
            }
            //нашли customers и apartments
        }

        return new ResultQuery(count, Arrays.asList(accommodations, customers, apartments));
    }

    @Override
    public ResultQuery findAll(String sort) throws Exception {

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

        int count = accommodations.size();

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

        customers = new ArrayList<>();
        apartments = new ArrayList<>();

        for (Accommodation accommodation : accommodations) {
            customers.add(customerDao.findById(accommodation.getCustomer_id()));
            apartments.add(apartmentDao.findById(accommodation.getApartment_id()));
        }
        //нашли customers и apartments


        return new ResultQuery(count, Arrays.asList(accommodations, customers, apartments));
    }

    @Override
    public ResultQuery findForDate(Date arrival_date, Date departure_date) throws Exception {
        ResultQuery resultQuery = findAll("arrival_date");

        List<Accommodation> accommodations = (List<Accommodation>) resultQuery.getList().get(0);
        List<Customer> customers = (List<Customer>) resultQuery.getList().get(1);
        List<Apartment> apartments = (List<Apartment>) resultQuery.getList().get(2);

        List<Accommodation> accommodationList = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        List<Apartment> apartmentList = new ArrayList<>();

        for (int i = 0; i < accommodations.size(); i++) {
            if (
                    accommodations.get(i).getArrival_date().after(arrival_date)
                            && accommodations.get(i).getArrival_date().before(departure_date)

                            ||

                            accommodations.get(i).getDeparture_date().after(arrival_date)
                                    && (accommodations.get(i).getDeparture_date().before(departure_date)
                                    || accommodations.get(i).getDeparture_date().equals(departure_date))
            ) {
                accommodationList.add(accommodations.get(i));
                customerList.add(customers.get(i));
                apartmentList.add(apartments.get(i));
            }
        }

        return new ResultQuery(accommodations.size(), Arrays.asList(accommodationList, customerList, apartmentList));
    }

    @Override
    public ResultQuery filter(int start,
                              int total,
                              String sort,
                              String fio,
                              String date,
                              Integer apart) throws Exception {

        start -= 1;
        total += start;

        ResultQuery resultQuery = findAll(sort);

        List<Accommodation> accommodations = (List<Accommodation>) resultQuery.getList().get(0);
        List<Customer> customers = (List<Customer>) resultQuery.getList().get(1);
        List<Apartment> apartments = (List<Apartment>) resultQuery.getList().get(2);

        for (int k = 0; k < accommodations.size(); k++) {
            if (!fio.equals("")) {
                String fio2 = customers.get(k).getSurname() + " " + customers.get(k).getName() + " " +
                        customers.get(k).getPatronymic();
                if (fio2.toLowerCase().lastIndexOf(fio.toLowerCase()) == -1) {
                    accommodations.remove(k);
                    customers.remove(k);
                    apartments.remove(k--);
                    continue;
                }
            }
            if (!date.equals("")) {
                if (accommodations.get(k).getArrival_date().toString().toLowerCase().lastIndexOf(date.toLowerCase()) == -1) {
                    accommodations.remove(k);
                    customers.remove(k);
                    apartments.remove(k--);
                    continue;
                }
            }
            if (apart != null) {
                if (apartments.get(k).getNumber() != apart) {
                    accommodations.remove(k);
                    customers.remove(k);
                    apartments.remove(k--);
                    continue;
                }
            }
        }

        int count = accommodations.size();

        if (accommodations.size() < total) {
            total = accommodations.size();
        }

        if (start <= total) {
            accommodations = new ArrayList<>(accommodations.subList(start, total));
            apartments = new ArrayList<>(apartments.subList(start, total));
            customers = new ArrayList<>(customers.subList(start, total));
        }

        return new ResultQuery(count, Arrays.asList(accommodations, customers, apartments));
    }

    @Override
    public ResultQuery searchForCurrentDate(int start,
                                            int total,
                                            String sort) throws Exception {

        start -= 1;
        total += start;

        ResultQuery resultQuery = findAll(sort);

        List<Accommodation> accommodations = (List<Accommodation>) resultQuery.getList().get(0);
        List<Customer> customers = (List<Customer>) resultQuery.getList().get(1);
        List<Apartment> apartments = (List<Apartment>) resultQuery.getList().get(2);

        List<Accommodation> newAccommodations = new ArrayList<>();
        List<Customer> newCustomers = new ArrayList<>();
        List<Apartment> newApartments = new ArrayList<>();

        Date today = new Date(System.currentTimeMillis());
        today = Date.valueOf(String.valueOf(today));

        for (int k = 0; k < accommodations.size(); k++) {

            if (
                    (today.equals(accommodations.get(k).getArrival_date()) &&
                            today.before(accommodations.get(k).getDeparture_date()))

                            ||

                            (today.after(accommodations.get(k).getArrival_date()) &&
                                    today.equals(accommodations.get(k).getDeparture_date()))

                            ||

                            (today.equals(accommodations.get(k).getArrival_date()) &&
                                    today.equals(accommodations.get(k).getDeparture_date()))

            ) {

                newCustomers.add(customers.get(k));
                newApartments.add(apartments.get(k));
                newAccommodations.add(accommodations.get(k));

            }
        }

        int count = newAccommodations.size();

        if (newAccommodations.size() < total) {
            total = newAccommodations.size();
        }

        if (start <= total) {
            newAccommodations = new ArrayList<>(newAccommodations.subList(start, total));
            newApartments = new ArrayList<>(newApartments.subList(start, total));
            newCustomers = new ArrayList<>(newCustomers.subList(start, total));
        }

        return new ResultQuery(count, Arrays.asList(newAccommodations, newCustomers, newApartments));
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
