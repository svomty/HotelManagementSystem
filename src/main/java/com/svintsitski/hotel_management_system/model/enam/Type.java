package com.svintsitski.hotel_management_system.model.enam;

public enum Type {
    Люкс("Люкс"),
    Стандарт("Простой");

    String type;

    Type(String type) {
        this.type = type;
    }

    public static Type findType(String type) {
        Type result = Type.Стандарт;
        if (type != null) {
            for (Type t : Type.values()) {
                if (type.toLowerCase().equals(t.toString().toLowerCase())) {
                    result = t;
                    break;
                }
            }
        }
        return result;
    }

    public static Type findTypeName(String type) {
        Type result = Type.Стандарт;
        if (type != null) {
            for (Type t : Type.values()) {
                if (type.toLowerCase().equals(t.name().toLowerCase())) {
                    result = t;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return type;
    }

    public String getValue() {
        return name();
    }

}
