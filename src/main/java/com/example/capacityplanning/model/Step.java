package com.example.capacityplanning.model;

public class Step {
    private String machine;
    private int duration;

    public String getMachine() {
        return this.machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Step{" +
                "machine='" + machine + '\'' +
                ", duration=" + duration +
                '}';
    }
}
