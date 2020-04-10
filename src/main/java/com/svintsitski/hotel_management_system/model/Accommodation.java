package com.svintsitski.hotel_management_system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Id;

@Entity
public class Accommodation {
    private @Id
    @GeneratedValue
    int id;
    private Date arrival_date;
    private Date departure_date;
    private int customer_id;
    private int apartment_id;

    public Accommodation() {
    }

    public Accommodation(Date arrival_date, Date departure_date, int customer_id, int apartment_id) {
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.customer_id = customer_id;
        this.apartment_id = apartment_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        }
    }

    public Date getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(Date arrival_date) {
        this.arrival_date = arrival_date;
    }

    public Date getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
        this.departure_date = departure_date;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(int apartment_id) {
        this.apartment_id = apartment_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accommodation that = (Accommodation) o;
        return id == that.id &&
                customer_id == that.customer_id &&
                apartment_id == that.apartment_id &&
                Objects.equals(arrival_date, that.arrival_date) &&
                Objects.equals(departure_date, that.departure_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, arrival_date, departure_date, customer_id, apartment_id);
    }

    @Override
    public String toString() {
        return "hotelAccommodation{" +
                "id=" + id +
                ", arrival_date=" + arrival_date +
                ", departure_date=" + departure_date +
                ", customer_id=" + customer_id +
                ", apartment_id=" + apartment_id +
                '}';
    }
}
