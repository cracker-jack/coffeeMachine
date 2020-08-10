package com.example.demo.services;

import com.example.demo.entities.CoffeeMachineInputRequest;
import com.example.demo.entities.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CoffeeMachineImpl implements CoffeeMachine {
    private final Map<String, Integer> ingredientsAvailable;
    private final Integer lowLevel;
    private final Integer outlets;
    private final Integer maxLevel;
    private final Map<String, Map<String, Integer>> beverageCompositonMap;
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeMachineImpl.class);

    public CoffeeMachineImpl(CoffeeMachineInputRequest coffeeMachineInputRequest, Integer lowLevel, Integer maxLevel) {
        this.ingredientsAvailable = coffeeMachineInputRequest.getMachine().getTotal_items_quantity();
        this.lowLevel = lowLevel;
        this.maxLevel = maxLevel;
        this.outlets = coffeeMachineInputRequest.getMachine().getOutlets().get("count_n");
        this.beverageCompositonMap = coffeeMachineInputRequest.getMachine().getBeverages();
    }

    @Override
    public List<String> getBeverage(List<Order> order) {
        List<String> result = new ArrayList<>();
        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(outlets);
            for (Order o : order) {
                Future<String> ans = executor.submit(() -> {
                    Map<String, Integer> beverageIngredientMap = beverageCompositonMap.get(o.getBeverage().toString());

                    if (beverageIngredientMap == null) {
                        return o.getBeverage().toString() + " cannot be created. Machine don't know to create it.";
                    } else {
                        List<String> checkAvailability = new ArrayList<>();
                        for (Map.Entry<String, Integer> m : beverageIngredientMap.entrySet()) {
                            if (!ingredientsAvailable.containsKey(m.getKey())) {
                                checkAvailability.add("Please provide ingredient: " + m.getKey());

                            } else if (ingredientsAvailable.get(m.getKey()) < m.getValue()) {
                                checkAvailability.add("ingredient: " + m.getKey() + " is unsufficent");
                            }
                        }
                        if (!checkAvailability.isEmpty()) {
                            return checkAvailability.stream().reduce(o.getBeverage().toString() + " cannot be created" +
                                    " ", (ele1, ele2) -> ele1 + ", " + ele2);
                        } else {
                            for (Map.Entry<String, Integer> m : beverageIngredientMap.entrySet()) {
                                Integer quant = ingredientsAvailable.get(m.getKey());
                                ingredientsAvailable.put(m.getKey(), quant - m.getValue());
                            }
                            return o.getBeverage().toString() + " is created.";
                        }
                    }


                });
                //LOGGER.info(executor.toString());
                result.add(ans.get(5, TimeUnit.SECONDS));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return result;
    }

    //resetting machine ingredients
    @Override
    public void resetMachine() {
        for (Map.Entry<String, Integer> m : ingredientsAvailable.entrySet()) {
            m.setValue(0);
        }
    }

    @Override
    public void refillIngredientsFully() {
        for (Map.Entry<String, Integer> m : ingredientsAvailable.entrySet()) {
            m.setValue(maxLevel);
        }
    }

    @Override
    public void refillOrAddNewIngredient(String key, Integer value) {
        if (ingredientsAvailable.get(key) == null) {
            ingredientsAvailable.put(key, Math.min(value, maxLevel));
        } else {
            ingredientsAvailable.put(key, Math.min(value + ingredientsAvailable.get(key), maxLevel));
        }
    }

    @Override
    public List<String> getLowLevelIngredients() {
        List<String> ans = new ArrayList<>();
        for (Map.Entry<String, Integer> m : ingredientsAvailable.entrySet()) {
            if (m.getValue() < lowLevel) {
                ans.add(m.getKey() + " is below level.Please refill asap.");
            }
        }
        return ans;
    }

    @Override
    public List<String> getCurrentIngredientLevel() {
        List<String> ans = new ArrayList<>();
        for (Map.Entry<String, Integer> m : ingredientsAvailable.entrySet()) {
            ans.add(m.getKey() + " : " + m.getValue());
        }
        return ans;
    }
}
