package com.svintsitski.hotel_management_system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Apartment {
    private @Id @GeneratedValue byte number;
    private byte type_id;
    private String description;

    public Apartment() {
    }

    public Apartment(byte type_id, String description) {
        this.type_id = type_id;
        this.description = description;
    }

    public byte getNumber() {
        return number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }

    public byte getType_id() {
        return type_id;
    }

    public void setType_id(byte type_id) {
        this.type_id = type_id;
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
                type_id == apartment.type_id &&
                Objects.equals(description, apartment.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type_id, description);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "number=" + number +
                ", type_id=" + type_id +
                ", description='" + description + '\'' +
                '}';
    }
}
