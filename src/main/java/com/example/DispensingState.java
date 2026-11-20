package com.example;

public class DispensingState implements StateOfVendingMachine {

    private final VendingMachine machine;

    public DispensingState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectSnack(String name) {
        throw new IllegalStateException(
            "Already dispensing");
    }

    @Override
    public void insertMoney(double amount) {
        throw new IllegalStateException(
            "Already dispensing");
    }

    @Override
    public void dispenseSnack() {
        boolean success =
            machine.getDispenser().dispense(
                machine.getSelectedSnack(),
                machine.getInventory());

        if (success) {
            machine.completeSale();
        } else {
            machine.refund(
                "Unable to dispense " +
                machine.getSelectedSnack());
        }
    }

    @Override
    public void cancel() {
        throw new IllegalStateException(
            "Already dispensing");
    }

    @Override
    public String getName() {
        return "DispensingSnack";
    }
}
