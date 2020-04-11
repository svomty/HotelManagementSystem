package com.svintsitski.hotel_management_system.model.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Apartment {
    private @Id
    @GeneratedValue
    int id;
    private byte number;
    private int type_id;

    public Apartment() {
    }

    public Apartment(byte number, int type_id) {
        this.number = number;
        this.type_id = type_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getNumber() {
        return number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return id == apartment.id &&
                number == apartment.number &&
                type_id == apartment.type_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, type_id);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", number=" + number +
                ", type_id=" + type_id +
                '}';
    }
}
