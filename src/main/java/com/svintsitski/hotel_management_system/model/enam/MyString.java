package com.svintsitski.hotel_management_system.model.enam;

import java.util.Objects;

public class MyString {
    String myText;

    public MyString(String myText) {
        this.myText = myText;
    }

    public MyString() {
    }

    public String getMyText() {
        return myText;
    }

    public void setMyText(String myText) {
        this.myText = myText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyString myString = (MyString) o;
        return Objects.equals(myText, myString.myText);
    }

    @Override
    public String toString() {
        return "MyString{" +
                "myText='" + myText + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(myText);
    }
}
