package com.example.demo.entities;

import java.util.Map;

public class Machine {

    Map<String, Integer> outlets;
    Map<String, Integer> total_items_quantity;
    Map<String, Map<String, Integer>> beverages;

    public Map<String, Integer> getOutlets() {
        return outlets;
    }

    public void setOutlets(Map<String, Integer> outlets) {
        this.outlets = outlets;
    }

    public Map<String, Integer> getTotal_items_quantity() {
        return total_items_quantity;
    }

    public void setTotal_items_quantity(Map<String, Integer> total_items_quantity) {
        this.total_items_quantity = total_items_quantity;
    }

    public Map<String, Map<String, Integer>> getBeverages() {
        return beverages;
    }

    public void setBeverages(Map<String, Map<String, Integer>> beverages) {
        this.beverages = beverages;
    }
}
