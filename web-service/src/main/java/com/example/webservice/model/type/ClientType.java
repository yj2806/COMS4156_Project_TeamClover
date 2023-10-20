package com.example.webservice.model.type;

// Enum that represents different types of clients
public enum ClientType {
    DISTRIBUTOR,         // Represents a distributor type of client
    NON_DISTRIBUTOR;     // Represents a non-distributor type of client

    // Check if a given string value is a valid client type
    public static boolean isValid(String value) {
        for (ClientType type : values()) {
            if (type.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    // Convert a string value to a ClientType enum value
    public static ClientType fromString(String value) {
        for (ClientType type : values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ClientType: " + value);
    }
}