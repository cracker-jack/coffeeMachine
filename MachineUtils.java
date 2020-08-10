package com.example.demo.services.Utils;

import com.example.demo.entities.BeverageEnum;

import java.util.Random;

public class MachineUtils {
    private MachineUtils() {
    }

    //function to generate random beverage
    public static BeverageEnum randomBeverage() {
        return BeverageEnum.class.getEnumConstants()[new Random().nextInt(BeverageEnum.class.getEnumConstants().length)];
    }
}
