package com.example.capacityplanning.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CapacityCalculator {

    private List<Machine> machines;
    private static List<Product> products;
    private Map<String, Integer> capacityDemand;
    private Map<String, Integer> capacitySupply;

    public CapacityCalculator() {
        machines = List.of(
            new Machine("Machine 1", 1),
            new Machine("Machine 2", 2),
            new Machine("Machine 3", 3),
            new Machine("Machine 4", 1),
            new Machine("Machine 5", 2)
        );

        if (products == null) readProductsData();
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

        return capacityDemand;
    }
    public Map<String, Integer> getCapacitySupply() {
        if (capacitySupply != null && !capacitySupply.isEmpty())
            return capacitySupply;

        capacitySupply = new HashMap<>();
        for (Machine machine : machines) {
            capacitySupply.put(machine.getName(), machine.getMonthlyCapacity());
        }

        return capacitySupply;
    }
    public void readProductsData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProductsJsonWrapper wrapper = objectMapper.readValue(new File("products-data.json"),
                    ProductsJsonWrapper.class);
            products = wrapper.getProducts();
            for (Product product : products) System.out.println(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
