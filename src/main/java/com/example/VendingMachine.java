package com.example;

import java.util.LinkedHashMap;
import java.util.Map;

public class VendingMachine {

    private final Map<String, Snack> inventory =
        new LinkedHashMap<>();

    private final SnackDispenseHandler dispenser;

    private StateOfVendingMachine state;
    private String selectedSnack;
    private double balance;
    private double lastChange;
    private double lastRefund;

    public VendingMachine() {

        String[] order = {
            "Coke", "Pepsi", "Cheetos",
            "Doritos", "KitKat", "Snickers"
        };

        dispenser = new SnackDispenseHandler(order[0]);

        SnackDispenseHandler current = dispenser;

        for (int i = 1; i < order.length; i++) {
            SnackDispenseHandler next =
                new SnackDispenseHandler(order[i]);

            current.setNext(next);
            current = next;
        }

        state = new IdleState(this);
    }

    public void addSnack(Snack snack) {
        if (snack == null ||
            inventory.containsKey(snack.getName())) {
            throw new IllegalArgumentException(
                "Snack is null or already stocked");
        }

        inventory.put(snack.getName(), snack);
    }

    public Snack getSnack(String name) {
        return inventory.get(name);
    }

    public String getStateName() {
        return state.getName();
    }

    public String getSelectedSnack() {
        return selectedSnack;
    }

    public double getBalance() {
        return balance;
    }

    public double getLastChange() {
        return lastChange;
    }

    public double getLastRefund() {
        return lastRefund;
    }

    public String getChainOrder() {
        StringBuilder result = new StringBuilder();

        SnackDispenseHandler handler = dispenser;

        while (handler != null) {
            if (result.length() > 0) {
                result.append(" -> ");
            }

            result.append(handler.getSnackName());
            handler = handler.getNext();
        }

        return result.toString();
    }

    public void selectSnack(String name) {
        state.selectSnack(name);
    }

    public void insertMoney(double amount) {
        state.insertMoney(amount);
    }

    public void dispenseSnack() {
        state.dispenseSnack();
    }

    public void cancel() {
        state.cancel();
    }

    void setState(StateOfVendingMachine nextState) {
        state = nextState;
    }

    void setSelectedSnack(String name) {
        selectedSnack = name;
        lastChange = 0;
        lastRefund = 0;
    }

    void addBalance(double amount) {
        balance = round(balance + amount);
    }

    SnackDispenseHandler getDispenser() {
        return dispenser;
    }

    Map<String, Snack> getInventory() {
        return inventory;
    }

    void completeSale() {
        Snack snack = getSnack(selectedSnack);

        lastChange = round(balance - snack.getPrice());
        lastRefund = 0;

        System.out.printf(
            "Dispensed: %s | Change: $%.2f%n",
            selectedSnack, lastChange);

        clearTransaction();
    }

    void refund(String reason) {
        lastRefund = balance;
        lastChange = 0;

        System.out.printf(
            "%s | Refund: $%.2f%n",
            reason, lastRefund);

        clearTransaction();
    }

    private void clearTransaction() {
        balance = 0;
        selectedSnack = null;
        state = new IdleState(this);
    }

    static double round(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}
