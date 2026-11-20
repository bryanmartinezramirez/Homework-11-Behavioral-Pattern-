package com.example;

public class Snack {

    private final String name;
    private final double price;
    private int quantity;

    public Snack(String name, double price, int quantity) {
        if (name == null || name.isBlank() ||
            price <= 0 || quantity < 0) {
            throw new IllegalArgumentException(
                "Invalid snack details");
        }

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean dispenseOne() {
        if (quantity == 0) {
            return false;
        }

        quantity--;
        return true;
    }
}
