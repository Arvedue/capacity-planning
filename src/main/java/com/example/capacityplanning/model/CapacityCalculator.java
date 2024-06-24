package com.example.capacityplanning.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CapacityCalculator {

    private final List<Machine> machines;
    private static List<Product> products;
    private Map<String, Integer> capacityDemand;
    private Map<String, Integer> capacitySupply;

    public CapacityCalculator() {
        machines = List.of(
            new Machine("Machine 1", 1),
            new Machine("Machine 2", 2),
            new Machine("Machine 3", 1),
            new Machine("Machine 4", 1),
            new Machine("Machine 5", 2)
        );

        if (products == null) readProductsData();
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Machine> getMachines() {
        return machines;
    }
    public Map<String, Integer> getCapacityDemand() {
        if (capacityDemand != null && !capacityDemand.isEmpty())
            return capacityDemand;

        capacityDemand = new HashMap<>();
        for (Product product : products) {
            for (Step step : product.getSteps()) {
                capacityDemand.put(step.getMachine(),
                        capacityDemand.getOrDefault(step.getMachine(), 0) + step.getDuration());
            }
        }

        System.out.println("Kapazitätsbedarf (capacity demand):");
        for (Map.Entry<String, Integer> entry : capacityDemand.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " Std/Monat");
        }

        return capacityDemand;
    }
    public Map<String, Integer> getCapacitySupply() {
        if (capacitySupply != null && !capacitySupply.isEmpty())
            return capacitySupply;

        capacitySupply = new HashMap<>();
        for (Machine machine : machines) {
            capacitySupply.put(machine.getName(), machine.getDailyCapacity());
        }

        System.out.println("\nKapazitätsangebot (capacity supply):");
        for (Map.Entry<String, Integer> entry : capacitySupply.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " Std/Monat");
        }

        return capacitySupply;
    }
    private void readProductsData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProductsJsonWrapper wrapper = objectMapper.readValue(new File("products-data.json"),
                    ProductsJsonWrapper.class);
            products = wrapper.getProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
