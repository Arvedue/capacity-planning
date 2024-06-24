package com.example.capacityplanning.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private List<Step> steps;

    public void addStep(Step step) {
        steps.add(step);
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeStep(Step step) {
        steps.remove(step);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", steps=" + steps +
                '}';
    }
}
