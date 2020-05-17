package com.svintsitski.hotel_management_system.model.support;

import java.util.Objects;

public class View {
    int page;
    int size;
    String sort;

    public View(int page, int size, String sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public View() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "View{" +
                "page=" + page +
                ", size=" + size +
                ", sort='" + sort + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return page == view.page &&
                size == view.size &&
                Objects.equals(sort, view.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, size, sort);
    }
}
