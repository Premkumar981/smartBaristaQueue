package com.smartbarista.queuesystem.dto;

public class SimulatedOrderDTO {
    private double arrivalTime; // Minutes
    private int prepTime; // Seconds
    private double waitTime; // Seconds
    private String baristaName;
    private String customerType;
    private boolean abandoned;
    private String drinkType;

    public SimulatedOrderDTO() {
    }

    public SimulatedOrderDTO(double arrivalTime, int prepTime, double waitTime, String baristaName,
            String customerType, boolean abandoned, String drinkType) {
        this.arrivalTime = arrivalTime;
        this.prepTime = prepTime;
        this.waitTime = waitTime;
        this.baristaName = baristaName;
        this.customerType = customerType;
        this.abandoned = abandoned;
        this.drinkType = drinkType;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public String getBaristaName() {
        return baristaName;
    }

    public void setBaristaName(String baristaName) {
        this.baristaName = baristaName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public boolean isAbandoned() {
        return abandoned;
    }

    public void setAbandoned(boolean abandoned) {
        this.abandoned = abandoned;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }
}
