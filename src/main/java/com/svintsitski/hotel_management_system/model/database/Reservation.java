package com.svintsitski.hotel_management_system.model.database;

import java.sql.Date;
import java.util.Objects;

public class Reservation extends Accommodation {
    private byte arrived;

    public Reservation() {
    }

    public Reservation(Date arrival_date, Date departure_date, int customer_id, int apartment_id, byte arrived) {
        super(arrival_date, departure_date, customer_id, apartment_id);
        this.arrived = arrived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reservation that = (Reservation) o;
        return arrived == that.arrived;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), arrived);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + super.getId() +
                ", arrival_date=" + super.getArrival_date() +
                ", departure_date=" + super.getDeparture_date() +
                ", customer_id=" + super.getCustomer_id() +
                ", apartment_id=" + super.getApartment_id() +
                ", arrived=" + arrived +
                '}';
    }
}
