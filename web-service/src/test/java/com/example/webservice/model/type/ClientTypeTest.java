package com.example.webservice.model.type;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTypeTest {

    @Test
    public void testIsValidWithValidValue() {
        assertTrue(ClientType.isValid("DISTRIBUTOR"));
        assertTrue(ClientType.isValid("NON_DISTRIBUTOR"));
    }

    @Test
    public void testIsValidWithInvalidValue() {
        assertFalse(ClientType.isValid("INVALID_TYPE"));
        assertFalse(ClientType.isValid("distributor")); // Case-insensitive check
    }

    @Test
    public void testFromStringWithValidValue() {
        assertEquals(ClientType.DISTRIBUTOR, ClientType.fromString("DISTRIBUTOR"));
        assertEquals(ClientType.NON_DISTRIBUTOR, ClientType.fromString("NON_DISTRIBUTOR"));
    }

    @Test
    public void testFromStringWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            ClientType.fromString("INVALID_TYPE");
        });
    }
}
