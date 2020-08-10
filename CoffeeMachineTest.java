package com.example.demo;

import com.example.demo.entities.CoffeeMachineInputRequest;
import com.example.demo.entities.Order;
import com.example.demo.services.CoffeeMachine;
import com.example.demo.services.CoffeeMachineFactory;
import com.example.demo.services.CoffeeMachineImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.services.Utils.MachineUtils.randomBeverage;


public class CoffeeMachineTest {

    private static GsonBuilder gsonBuilder = new GsonBuilder();
    private static Gson gson = gsonBuilder.create();
    private static final String INPUT_PATH_1 = "src\\main\\resources\\coffeemachine\\testcase1.txt";
    private static final String INPUT_PATH_2 = "src\\main\\resources\\coffeemachine\\testcase2.txt";
    private static final String ORDER_LIST_PATH_1 = "src\\main\\resources\\order\\order1.txt";
    private static final String ORDER_LIST_PATH_2 = "src\\main\\resources\\order\\order2.txt";


    @BeforeClass
    public static void setUp(){
    }


    @AfterClass
    public static void tearDown(){

    }



    @Test
    public void testcase1() throws IOException, FileNotFoundException {
        //setting coffee machine with input json
        File file = new File(INPUT_PATH_1);
        BufferedReader br = new BufferedReader(new FileReader(file));
        CoffeeMachineInputRequest coffeeMachineInputRequest = gson.fromJson(br.readLine(), CoffeeMachineInputRequest.class);
        CoffeeMachine coffeeMachine1 = CoffeeMachineFactory.createCoffeeMachine(coffeeMachineInputRequest,50,1000);

        //creating sample order cases
        File fileOrder = new File(ORDER_LIST_PATH_2);
        BufferedReader br2 = new BufferedReader(new FileReader(fileOrder));
        List<Order> order = gson.fromJson(br2, new TypeToken<List<Order>>() {
        }.getType());
        List<String> result = coffeeMachine1.getBeverage(order);
        for (String s : result) {
            System.out.println(s);
        }
    }


    @Test
    public void testcase2() throws IOException, FileNotFoundException {
        //setting coffee machine with input json
        File file = new File(INPUT_PATH_1);
        BufferedReader br = new BufferedReader(new FileReader(file));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        CoffeeMachineInputRequest coffeeMachineInputRequest = gson.fromJson(br.readLine(), CoffeeMachineInputRequest.class);
        CoffeeMachine coffeeMachine1 = new CoffeeMachineImpl(coffeeMachineInputRequest, 50, 1000);

        //creating sample order cases
        File fileOrder = new File(ORDER_LIST_PATH_2);
        BufferedReader br2 = new BufferedReader(new FileReader(fileOrder));
        List<Order> order = gson.fromJson(br2, new TypeToken<List<Order>>() {
        }.getType());

        List<String> result = coffeeMachine1.getBeverage(order); //initial cases
        System.out.println("initial orders");
        for (String s : result) {
            System.out.println(s);
        }
        List<String> lowLevelData = coffeeMachine1.getLowLevelIngredients(); // get low level ingreadients
        System.out.println("\nLow level ingredients in machine");
        for (String s : lowLevelData) {
            System.out.println(s);
        }

        List<String> currentIngredientLevel = coffeeMachine1.getCurrentIngredientLevel(); // get current ingreadient level
        System.out.println("\nCurrent ingredient level in machine");
        for (String s : currentIngredientLevel) {
            System.out.println(s);
        }

        coffeeMachine1.resetMachine();  //reseting machine
        System.out.println("\norders after resetting machine");
        List<String> result2 = coffeeMachine1.getBeverage(order);
        for (String s : result2) {
            System.out.println(s);
        }
        coffeeMachine1.refillIngredientsFully();  //refilling all ingredients fully
        System.out.println("\norders after refilling machine");
        List<String> result3 = coffeeMachine1.getBeverage(order);
        for (String s : result3) {
            System.out.println(s);
        }
        List<String> currentIngredientLevel2 = coffeeMachine1.getCurrentIngredientLevel(); // get current ingreadient level
        System.out.println("\nCurrent ingredient level in machine");
        for (String s : currentIngredientLevel2) {
            System.out.println(s);
        }

        coffeeMachine1.refillOrAddNewIngredient("green_mixture", 300);  //refilling green mixture
        System.out.println("\norders after refilling/adding  green mixture");
        List<String> result4 = coffeeMachine1.getBeverage(order);
        for (String s : result4) {
            System.out.println(s);
        }
        List<String> currentIngredientLevel3 = coffeeMachine1.getCurrentIngredientLevel(); // get current ingreadient level
        System.out.println("\nCurrent ingredient level in machine");
        for (String s : currentIngredientLevel3) {
            System.out.println(s);
        }
    }

    @Test
    public void testcase3() throws IOException, FileNotFoundException {
        //setting coffee machine with input json
        File file = new File(INPUT_PATH_1);
        BufferedReader br = new BufferedReader(new FileReader(file));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        CoffeeMachineInputRequest coffeeMachineInputRequest = gson.fromJson(br.readLine(), CoffeeMachineInputRequest.class);
        CoffeeMachine coffeeMachine1 = new CoffeeMachineImpl(coffeeMachineInputRequest, 50, 1000);


        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setBeverage(randomBeverage()); //getting random orders
            orderList.add(order);
        }
        List<String> result = coffeeMachine1.getBeverage(orderList);
        for (String s : result) {
            System.out.println(s);
        }
    }


}
