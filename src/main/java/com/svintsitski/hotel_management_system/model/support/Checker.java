package com.svintsitski.hotel_management_system.model.support;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Checker {
    private boolean check;

    public Checker(boolean check) {
        this.check = check;
    }

    public Checker() {
        this.check = false;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public static List<Date> validateDateForAccommodation(Optional<String> arrival_date_filter,
                                                          Optional<String> departure_date_filter) {
        String filter_arrival_date = arrival_date_filter.orElse("1980-01-01");
        String filter_departure_date = departure_date_filter.orElse("1980-01-01");

        if (filter_arrival_date.equals("") || filter_departure_date.equals("")) {
            filter_arrival_date = "1980-01-01";
            filter_departure_date = "1980-01-01";
        }

        Date arrival_date = Date.valueOf(filter_arrival_date);
        Date departure_date = Date.valueOf(filter_departure_date);

        if (departure_date.before(arrival_date)) {
            Date tmp = arrival_date;
            arrival_date = departure_date;
            departure_date = tmp;
        }

        return Arrays.asList(arrival_date, departure_date);
    }

}
