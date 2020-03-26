package com.svintsitski.hotel_management_system.model;

import java.util.List;

public class ResultQuery {
    private int count;
    private List<ApartmentType> apartmentTypeList;

    public ResultQuery(int count, List<ApartmentType> apartmentTypeList) {
        this.count = count;
        this.apartmentTypeList = apartmentTypeList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ApartmentType> getApartmentTypeList() {
        return apartmentTypeList;
    }

    public void setApartmentTypeList(List<ApartmentType> apartmentTypeList) {
        this.apartmentTypeList = apartmentTypeList;
    }
}
