package com.example.webservice.model.type;

public enum ClientType {
    DISTRIBUTOR,
    NON_DISTRIBUTOR;

    public static boolean isValid(String value) {
        for (ClientType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static ClientType fromString(String value) {
        for (ClientType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ClientType: " + value);
    }
}