package com.example;

public class IdleState implements StateOfVendingMachine {

    private final VendingMachine machine;

    public IdleState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectSnack(String name) {

        Snack snack = machine.getSnack(name);

        if (snack == null) {
            System.out.println("Unknown snack: " + name);
            return;
        }

        machine.setSelectedSnack(name);
        machine.setState(new WaitingForMoneyState(machine));

        System.out.printf(
            "Selected: %s ($%.2f) | Waiting for money%n",
            name, snack.getPrice());
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Select a snack first.");
    }

    @Override
    public void dispenseSnack() {
        System.out.println("Select a snack first.");
    }

    @Override
    public void cancel() {
        System.out.println("No transaction to cancel.");
    }

    @Override
    public String getName() {
        return "Idle";
    }
}
