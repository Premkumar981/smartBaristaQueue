package com.smartbarista.queuesystem.service;

import com.smartbarista.queuesystem.dto.SimulatedOrderDTO;
import com.smartbarista.queuesystem.dto.SimulationResultDTO;
import com.smartbarista.queuesystem.model.DrinkType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimulationBatchService {

    private final Random random = new Random();

    public List<SimulationResultDTO> runMultipleTestCases(int numTestCases) {
        List<SimulationResultDTO> results = new ArrayList<>();
        for (int i = 1; i <= numTestCases; i++) {
            results.add(runSingleTestCase(i));
        }
        return results;
    }

    private SimulationResultDTO runSingleTestCase(int id) {
        int numOrders = 200 + random.nextInt(101); // 200 - 300
        List<SimulatedOrder> orders = generateOrders(numOrders);
        List<SimulatedOrderDTO> orderDetailDTOs = new ArrayList<>();

        // Simulate with 3 baristas
        int baristaCount = 3; // Defined for clarity, was hardcoded as 3
        double[] baristaFreeTime = new double[baristaCount]; // Time in minutes when barista becomes free
        double totalWaitTime = 0;
        int abandonedCount = 0;
        Map<String, Integer> performance = new HashMap<>();
        for (int i = 1; i <= baristaCount; i++)
            performance.put("Barista " + i, 0);

        for (SimulatedOrder order : orders) {
            // Find barista who becomes free soonest
            int bestBarista = 0;
            for (int i = 1; i < baristaCount; i++) {
                if (baristaFreeTime[i] < baristaFreeTime[bestBarista])
                    bestBarista = i;
            }

            double startTime = Math.max(order.arrivalTime, baristaFreeTime[bestBarista]);
            double waitTime = startTime - order.arrivalTime;

            // Abandonment Logic: NEW (8m), REGULAR (10m)
            boolean abandoned = false;
            if (order.customerType.equals("NEW") && waitTime >= 8.0) {
                abandoned = true;
            } else if (order.customerType.equals("REGULAR") && waitTime >= 10.0) {
                abandoned = true;
            }

            if (!abandoned) {
                totalWaitTime += waitTime;
                baristaFreeTime[bestBarista] = startTime + order.prepTime;
                String baristaName = "Barista " + (bestBarista + 1);
                performance.put(baristaName, performance.get(baristaName) + 1);

                orderDetailDTOs.add(new SimulatedOrderDTO(
                        order.arrivalTime,
                        order.prepTime * 60,
                        waitTime * 60,
                        baristaName,
                        order.customerType,
                        false,
                        order.drinkType));
            } else {
                abandonedCount++;
                orderDetailDTOs.add(new SimulatedOrderDTO(
                        order.arrivalTime,
                        0, // No prep time if abandoned
                        waitTime * 60,
                        "NONE",
                        order.customerType,
                        true,
                        order.drinkType));
            }
        }

        int servedOrders = numOrders - abandonedCount;
        double avgWait = servedOrders > 0 ? (totalWaitTime * 60) / servedOrders : 0;
        return new SimulationResultDTO(id, numOrders, abandonedCount, avgWait, performance, orderDetailDTOs);
    }

    private List<SimulatedOrder> generateOrders(int count) {
        List<SimulatedOrder> orders = new ArrayList<>();
        double currentTime = 0;
        for (int i = 0; i < count; i++) {
            currentTime += (random.nextDouble() * 2); // Average 1 order every minute
            DrinkType type = DrinkType.values()[random.nextInt(DrinkType.values().length)];
            int prepTime = type.getPrepTime();
            String customerType = random.nextDouble() < 0.3 ? "NEW" : "REGULAR";
            orders.add(new SimulatedOrder(currentTime, prepTime, customerType, type.name()));
        }
        return orders;
    }

    private static class SimulatedOrder {
        double arrivalTime;
        int prepTime;
        String customerType;
        String drinkType;

        SimulatedOrder(double arrivalTime, int prepTime, String customerType, String drinkType) {
            this.arrivalTime = arrivalTime;
            this.prepTime = prepTime;
            this.customerType = customerType;
            this.drinkType = drinkType;
        }
    }
}
