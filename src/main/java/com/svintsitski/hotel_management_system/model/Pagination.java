package com.svintsitski.hotel_management_system.model;

import com.svintsitski.hotel_management_system.controller.MainController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

import static com.svintsitski.hotel_management_system.controller.MainController.default_page_size;

public class Pagination {
    int current_page;
    int start_page;
    int page_size;
    int startElem;

    public Pagination(int page, int size) {
        current_page = page;
        start_page = Math.max(current_page - 2, 1);

        page_size = size;
        if (page_size < 1) {
            page_size = default_page_size;
        }

        int startElem = 1 + (current_page - 1) * page_size;
    }

    public int getTotalPage(float full_elem_count) {
        return Math.max((int) Math.ceil( full_elem_count / (float) getPage_size()), 1);
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getStart_page() {
        return start_page;
    }

    public int getPage_size() {
        return page_size;
    }

    public int getStartElem() {
        return startElem;
    }
}
