package com.svintsitski.hotel_management_system.model.support;

import com.svintsitski.hotel_management_system.model.Config;

public class Pagination {
    private int current_page;
    private int start_page;
    private int page_size;
    private int startElem;

    public Pagination(int page, int size) {
        current_page = page;
        start_page = Math.max(current_page - 2, 1);

        page_size = size;
        if (page_size < 1) {
            page_size = Config.getInstance().getCountElem();
        }

        startElem = 1 + (current_page - 1) * page_size;
    }

    public int getTotalPage(float full_elem_count) {
        return Math.max((int) Math.ceil(full_elem_count / (float) getPage_size()), 1);
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
