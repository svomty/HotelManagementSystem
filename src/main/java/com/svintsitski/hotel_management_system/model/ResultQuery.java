package com.svintsitski.hotel_management_system.model;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Objects;

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

    public static int getLastInsertId(JdbcTemplate jdbcTemplate) {
        String sql2 = "SELECT LAST_INSERT_ID();";
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql2, Integer.class));
    }
}
