package com.example;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SnackDispenseHandlerTest {

    @Test
    void testChainOrder() {
        SnackDispenseHandler coke =
            new SnackDispenseHandler("Coke");

        SnackDispenseHandler pepsi =
            new SnackDispenseHandler("Pepsi");

        coke.setNext(pepsi);

        assertEquals("Coke", coke.getSnackName());
        assertSame(pepsi, coke.getNext());
    }

    @Test
    void testDispenseCorrectSnack() {
        SnackDispenseHandler coke =
            new SnackDispenseHandler("Coke");

        SnackDispenseHandler pepsi =
            new SnackDispenseHandler("Pepsi");

        coke.setNext(pepsi);

        Map<String, Snack> inventory = new HashMap<>();

        inventory.put("Coke", new Snack("Coke", 1.50, 2));
        inventory.put("Pepsi", new Snack("Pepsi", 1.75, 1));

        assertTrue(coke.dispense("Pepsi", inventory));

        assertEquals(0, inventory.get("Pepsi").getQuantity());
        assertEquals(2, inventory.get("Coke").getQuantity());
    }

    @Test
    void testSoldOutSnack() {
        SnackDispenseHandler snickers =
            new SnackDispenseHandler("Snickers");

        Map<String, Snack> inventory = new HashMap<>();

        inventory.put("Snickers",
            new Snack("Snickers", 1.75, 0));

        assertFalse(
            snickers.dispense("Snickers", inventory));

        assertFalse(
            snickers.dispense("Unknown", inventory));
    }
}
