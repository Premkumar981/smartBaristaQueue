package com.smartbarista.queuesystem.dto;

import java.util.List;
import java.util.Map;

public class SimulationResultDTO {
    private int testCaseId;
    private int totalOrders;
    private int abandonedOrders;
    private double averageWaitTime; // in seconds
    private Map<String, Integer> baristaPerformance; // Barista Name -> Drinks Prepared
    private List<SimulatedOrderDTO> orders;

    public SimulationResultDTO() {
    }

    public SimulationResultDTO(int testCaseId, int totalOrders, int abandonedOrders, double averageWaitTime,
            Map<String, Integer> baristaPerformance, List<SimulatedOrderDTO> orders) {
        this.testCaseId = testCaseId;
        this.totalOrders = totalOrders;
        this.abandonedOrders = abandonedOrders;
        this.averageWaitTime = averageWaitTime;
        this.baristaPerformance = baristaPerformance;
        this.orders = orders;
    }

    public int getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(int testCaseId) {
        this.testCaseId = testCaseId;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getAbandonedOrders() {
        return abandonedOrders;
    }

    public void setAbandonedOrders(int abandonedOrders) {
        this.abandonedOrders = abandonedOrders;
    }

    public double getAverageWaitTime() {
        return averageWaitTime;
    }

    public void setAverageWaitTime(double averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }

    public Map<String, Integer> getBaristaPerformance() {
        return baristaPerformance;
    }

    public void setBaristaPerformance(Map<String, Integer> baristaPerformance) {
        this.baristaPerformance = baristaPerformance;
    }

    public List<SimulatedOrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<SimulatedOrderDTO> orders) {
        this.orders = orders;
    }
}
