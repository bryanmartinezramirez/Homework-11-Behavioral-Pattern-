package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnackTest {

    @Test
    void testSnackProperties() {
        Snack snack = new Snack("Coke", 1.50, 3);

        assertEquals("Coke", snack.getName());
        assertEquals(1.50, snack.getPrice(), 0.001);
        assertEquals(3, snack.getQuantity());
    }

    @Test
    void testDispenseSnack() {
        Snack snack = new Snack("Snickers", 1.75, 1);

        assertTrue(snack.dispenseOne());
        assertEquals(0, snack.getQuantity());

        assertFalse(snack.dispenseOne());
        assertEquals(0, snack.getQuantity());
    }

    @Test
    void testInvalidSnack() {
        assertThrows(IllegalArgumentException.class,
            () -> new Snack("", 1.50, 2));

        assertThrows(IllegalArgumentException.class,
            () -> new Snack("Coke", -1.00, 2));

        assertThrows(IllegalArgumentException.class,
            () -> new Snack("Coke", 1.50, -1));
    }
}
