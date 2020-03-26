package com.svintsitski.hotel_management_system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ApartmentType {
    private @Id @GeneratedValue int id;
    private float price;
    private byte rooms_number;
    private byte places_number;
    private String type;
    private String description;

    public ApartmentType() {
    }

    public ApartmentType(float price, byte rooms_number, byte places_number, String type, String description) {
        this.setPrice(price);
        this.setRooms_number(rooms_number);
        this.setPlaces_number(places_number);
        this.setType(type);
        this.setDescription(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte getRooms_number() {
        return rooms_number;
    }

    public void setRooms_number(byte rooms_number) {
        if (rooms_number > 0 && rooms_number < 10) {
            this.rooms_number = rooms_number;
        } else {
            this.rooms_number = 1;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type != null){
            this.type = type.trim();
        }
        if (this.type == null ||  this.type.equals("")) {
            this.type = "простой";
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null){
            this.description = description.trim();
        }
        if (this.description == null ||  this.description.equals("")) {
            this.description = "описание отсутсвует";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentType that = (ApartmentType) o;
        return id == that.id &&
                Float.compare(that.price, price) == 0 &&
                rooms_number == that.rooms_number &&
                places_number == that.places_number &&
                Objects.equals(type, that.type) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, rooms_number, places_number, type, description);
    }

    @Override
    public String toString() {
        return "ApartmentType{" +
                "id=" + id +
                ", price=" + price +
                ", rooms_number=" + rooms_number +
                ", places_number=" + places_number +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
