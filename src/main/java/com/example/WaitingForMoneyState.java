package com.example;

import java.math.BigDecimal;

public class WaitingForMoneyState implements StateOfVendingMachine {

    private final VendingMachine machine;

    public WaitingForMoneyState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectSnack(String name) {
        if (machine.getSnack(name) == null) {
            System.out.println("Unknown snack: " + name);
            return;
        }

        machine.setSelectedSnack(name);
        System.out.println("Selection changed to: " + name);
    }

    @Override
    public void insertMoney(double amount) {
        if (!Double.isFinite(amount) ||
            amount <= 0 ||
            BigDecimal.valueOf(amount).scale() > 2) {

            throw new IllegalArgumentException(
                "Insert a positive amount in cents");
        }

        machine.addBalance(amount);

        System.out.printf(
            "Inserted: $%.2f | Balance: $%.2f%n",
            amount, machine.getBalance());

        machine.dispenseSnack();
    }

    @Override
    public void dispenseSnack() {
        Snack snack =
            machine.getSnack(machine.getSelectedSnack());

        if (machine.getBalance() + 0.000001 <
            snack.getPrice()) {

            System.out.printf(
                "Need $%.2f more for %s%n",
                VendingMachine.round(
                    snack.getPrice() - machine.getBalance()),
                snack.getName());

            return;
        }

        if (snack.getQuantity() == 0) {
            machine.refund(
                "Out of stock: " + snack.getName());
            return;
        }

        machine.setState(new DispensingState(machine));
        machine.dispenseSnack();
    }

    @Override
    public void cancel() {
        machine.refund("Cancelled");
    }

    @Override
    public String getName() {
        return "WaitingForMoney";
    }
}
