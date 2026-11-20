package com.example;

import java.util.Map;

public class SnackDispenseHandler {

    private final String snackName;
    private SnackDispenseHandler next;

    public SnackDispenseHandler(String snackName) {
        this.snackName = snackName;
    }

    public String getSnackName() {
        return snackName;
    }

    public SnackDispenseHandler getNext() {
        return next;
    }

    public void setNext(SnackDispenseHandler next) {
        this.next = next;
    }

    public boolean dispense(
            String requestedName,
            Map<String, Snack> inventory) {

        if (snackName.equalsIgnoreCase(requestedName)) {
            Snack snack = inventory.get(snackName);

            return snack != null && snack.dispenseOne();
        }

        if (next != null) {
            return next.dispense(requestedName, inventory);
        }

        return false;
    }
}
