package com.example.demo.services;

import com.example.demo.entities.Order;

import java.util.List;

public interface CoffeeMachine {
    public List<String> getBeverage(List<Order> order);
    public void resetMachine();
    public void refillIngredientsFully();
    public void refillOrAddNewIngredient(String key , Integer Value );
    public List<String> getLowLevelIngredients();
    public List<String> getCurrentIngredientLevel();
}
