package com.svintsitski.hotel_management_system.model.database;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

public class Reservation {
    private @Id
    @GeneratedValue
    int id;
    private Date arrival_date;
    private Date departure_date;
    private int apartment_id;
    private byte arrived;
    private String full_name;
    private String customer_phone;
    private String UUID;

    public Reservation() {
    }

    public Reservation(int id, Date arrival_date, Date departure_date, int apartment_id, byte arrived, String full_name, String customer_phone, String UUID) {
        this.id = id;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.apartment_id = apartment_id;
        this.arrived = arrived;
        this.full_name = full_name;
        this.customer_phone = customer_phone;
        this.UUID = UUID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public byte getArrived() {
        return arrived;
    }

    public void setArrived(byte arrived) {
        this.arrived = arrived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(int apartment_id) {
        this.apartment_id = apartment_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id &&
                apartment_id == that.apartment_id &&
                arrived == that.arrived &&
                Objects.equals(arrival_date, that.arrival_date) &&
                Objects.equals(departure_date, that.departure_date) &&
                Objects.equals(full_name, that.full_name) &&
                Objects.equals(customer_phone, that.customer_phone) &&
                Objects.equals(UUID, that.UUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, arrival_date, departure_date, apartment_id, arrived, full_name, customer_phone, UUID);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", arrival_date=" + arrival_date +
                ", departure_date=" + departure_date +
                ", apartment_id=" + apartment_id +
                ", arrived=" + arrived +
                ", full_name='" + full_name + '\'' +
                ", customer_phone='" + customer_phone + '\'' +
                ", UUID='" + UUID + '\'' +
                '}';
    }
}
