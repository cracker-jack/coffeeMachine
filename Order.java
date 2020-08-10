package com.example.demo.entities;

public class Order {
    private BeverageEnum beverage;

    public BeverageEnum getBeverage() {
        return beverage;
    }

    public void setBeverage(BeverageEnum beverage) {
        this.beverage = beverage;
    }
}
