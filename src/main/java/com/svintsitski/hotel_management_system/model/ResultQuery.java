package com.svintsitski.hotel_management_system.model;

import java.util.List;

public class ResultQuery {
    private int count;
    private List list;

    public ResultQuery(int count, List apartmentTypeList) {
        this.count = count;
        this.list = apartmentTypeList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
