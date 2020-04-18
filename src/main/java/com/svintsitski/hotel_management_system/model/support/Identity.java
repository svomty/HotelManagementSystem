package com.svintsitski.hotel_management_system.model.support;

import java.util.Objects;

public class Identity {
    private int identificator;

    public void setIdentificator(int id) {
        this.identificator = id;
    }

    public int getIdentificator() {
        return identificator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identity id1 = (Identity) o;
        return identificator == id1.identificator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificator);
    }

    @Override
    public String toString() {
        return "Id{" +
                "id=" + identificator +
                '}';
    }

    public Identity(int id) {
        this.identificator = id;
    }

    public Identity() {
        identificator = 0;
    }
}
