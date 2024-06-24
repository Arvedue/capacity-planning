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
        Map<String, Integer> capacityDemand = new HashMap<>();
        for (Product product : products) {
            for (Step step : product.getSteps()) {
                capacityDemand.put(step.getMachine(),
                        capacityDemand.getOrDefault(step.getMachine(), 0) + step.getDuration());
            }
        }

        return capacityDemand;
    }

    public void calculateCapacityDemand() {

    }
    public Map<String, Integer> getCapacitySupply() {
        Map<String, Integer> capacitySupply = new HashMap<>();
        for (Machine machine : machines) {
            capacitySupply.put(machine.getName(), machine.getDailyCapacity());
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

    public Machine getMachineByName(String name) {
        for (Machine machine : machines) {
            if (machine.getName().equals(name))
                return machine;
        }

        return null;
    }
}
