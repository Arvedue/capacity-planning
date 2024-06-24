package com.example.capacityplanning.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private List<Step> steps;

    public Product(String name) {
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public List<Step> getSteps() {
        return steps;
    }
}
