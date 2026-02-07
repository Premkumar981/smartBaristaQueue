package com.smartbarista.queuesystem.model;

public enum DrinkType {
    COLD_BREW(1, 0.25, 120),
    ESPRESSO(2, 0.20, 150),
    AMERICANO(2, 0.15, 140),
    CAPPUCCINO(4, 0.20, 180),
    LATTE(4, 0.12, 200),
    MOCHA(6, 0.08, 250);

    private final int prepTime;
    private final double frequency;
    private final int price;

    DrinkType(int prepTime, double frequency, int price) {
        this.prepTime = prepTime;
        this.frequency = frequency;
        this.price = price;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public double getFrequency() {
        return frequency;
    }

    public int getPrice() {
        return price;
    }
}
