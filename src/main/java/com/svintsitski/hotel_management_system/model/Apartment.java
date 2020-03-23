package com.svintsitski.hotel_management_system.model;

import java.util.Objects;

public class Apartment {
    private byte number;
    private byte rooms_number;
    private byte places_number;
    private byte price_id;
    private String description;

    public Apartment() {
    }

    public Apartment(byte number, byte rooms_number, byte places_number, byte price_id, String description) {
        this.number = number;
        this.rooms_number = rooms_number;
        this.places_number = places_number;
        this.price_id = price_id;
        this.description = description;
    }

    public byte getNumber() {
        return number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }

    public byte getRooms_number() {
        return rooms_number;
    }

    public void setRooms_number(byte rooms_number) {
        if (rooms_number > 0 && rooms_number < 10) {
            this.rooms_number = rooms_number;
        }
    }

    public byte getPlaces_number() {
        return places_number;
    }

    public void setPlaces_number(byte places_number) {
        if (places_number > 0 && places_number < 10) {
            this.places_number = places_number;
        }
    }

    public byte getPrice_id() {
        return price_id;
    }

    public void setPrice_id(byte price_id) {
        this.price_id = price_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.trim().substring(0, 89);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return number == apartment.number &&
                rooms_number == apartment.rooms_number &&
                places_number == apartment.places_number &&
                price_id == apartment.price_id &&
                Objects.equals(description, apartment.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, rooms_number, places_number, price_id, description);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "number=" + number +
                ", rooms_number=" + rooms_number +
                ", places_number=" + places_number +
                ", price_id=" + price_id +
                ", description='" + description + '\'' +
                '}';
    }
}
