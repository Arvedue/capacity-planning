package com.example.capacityplanning.model;

public class Step {
    private Machine machine;
    private int duration;

    public Step(Machine machine, int duration) {
        this.machine = machine;
        this.duration = duration;
    }

    public Machine getMachine() {
        return this.machine;
    }

    public int getDuration() {
        return this.duration;
    }
}
