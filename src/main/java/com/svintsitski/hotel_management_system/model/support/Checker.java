package com.svintsitski.hotel_management_system.model.support;

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

}
