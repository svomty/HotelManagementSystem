package com.svintsitski.hotel_management_system.model;

import java.util.Objects;

public class ApartmentPrice {
    private byte id;
    private float price;

    public ApartmentPrice() {
    }

    public ApartmentPrice(byte id, float price) {
        this.id = id;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentPrice that = (ApartmentPrice) o;
        return id == that.id &&
                Float.compare(that.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price);
    }

    @Override
    public String toString() {
        return "ApartmentPrice{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}
