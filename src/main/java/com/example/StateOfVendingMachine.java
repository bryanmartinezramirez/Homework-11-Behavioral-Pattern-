package com.example;

public interface StateOfVendingMachine {

    void selectSnack(String name);

    void insertMoney(double amount);

    void dispenseSnack();

    void cancel();

    String getName();
}
