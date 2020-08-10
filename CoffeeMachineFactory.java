package com.example.demo.services;

import com.example.demo.entities.CoffeeMachineInputRequest;

public class CoffeeMachineFactory {

    public static CoffeeMachine createCoffeeMachine(CoffeeMachineInputRequest coffeeMachineInputRequest, Integer
            threshold, Integer capacity) {
        return new CoffeeMachineImpl(coffeeMachineInputRequest, threshold, capacity);
    }

}
