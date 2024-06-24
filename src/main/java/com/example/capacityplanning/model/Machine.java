package com.example.capacityplanning.model;

public class Machine {
    private String name;
    private int quantity;
    private final int HOURS_PER_DAY = 8;
    private final int WORKING_DAYS_PER_WEEK = 5;
    private final int WEEKS_IN_MONTH = 4;

    public Machine(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public int getMonthlyCapacity() {
        return quantity * HOURS_PER_DAY * WORKING_DAYS_PER_WEEK * WEEKS_IN_MONTH;
    }
}
