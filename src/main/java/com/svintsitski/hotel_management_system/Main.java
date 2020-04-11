package com.svintsitski.hotel_management_system;

import com.svintsitski.hotel_management_system.model.enam.Type;

public class Main {
    public static void main(String[] args) {
        Type t1 = Type.SUITE;
        System.out.println(t1);
        System.out.println(t1.toString());
        System.out.println(Type.findType("ыфвыфв"));
        System.out.println(Type.findType("люкс"));
    }
}
