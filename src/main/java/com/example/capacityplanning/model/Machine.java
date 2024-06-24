package com.example.capacityplanning.model;

public class Machine {
    private String name;
    private int quantity;
    private int hoursPerDay;
    private int dayPerWeek;
    private final int WEEKS_IN_MONTH = 4;

    public Machine(String name, int quantity, int hoursPerDay, int dayPerWeek) {
        this.name = name;
        this.quantity = quantity;
        this.hoursPerDay = hoursPerDay;
        this.dayPerWeek = dayPerWeek;
    }

    public String getName() {
        return this.name;
    }

    public int getMonthlyCapacity() {
        return quantity * hoursPerDay * dayPerWeek * WEEKS_IN_MONTH;
    }
}
