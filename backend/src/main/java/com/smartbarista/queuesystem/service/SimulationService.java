package com.smartbarista.queuesystem.service;

import com.smartbarista.queuesystem.dto.OrderRequestDTO;
import com.smartbarista.queuesystem.model.CustomerType;
import com.smartbarista.queuesystem.model.DrinkType;
import com.smartbarista.queuesystem.model.LoyaltyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SimulationService {

    @Autowired
    private OrderService orderService;

    private boolean isSimulating = false;
    private final Random random = new Random();

    // λ = 1.4 customers/minute
    // Probability per 10 seconds calculation: 1 - exp(-1.4/6) approx 0.2
    private static final double ARRIVAL_PROBABILITY_10S = 0.208;

    public void startSimulation() {
        this.isSimulating = true;
    }

    public void stopSimulation() {
        this.isSimulating = false;
    }

    @Scheduled(fixedRate = 10000)
    public void simulateArrival() {
        if (!isSimulating)
            return;

        if (random.nextDouble() < ARRIVAL_PROBABILITY_10S) {
            OrderRequestDTO request = new OrderRequestDTO();
            request.setCustomerName("Customer " + (random.nextInt(900) + 100));
            request.setCustomerType(random.nextBoolean() ? CustomerType.NEW : CustomerType.REGULAR);

            // Random loyalty
            double loyaltyRand = random.nextDouble();
            if (loyaltyRand > 0.9)
                request.setLoyaltyStatus(LoyaltyStatus.GOLD);
            else if (loyaltyRand > 0.7)
                request.setLoyaltyStatus(LoyaltyStatus.SILVER);
            else
                request.setLoyaltyStatus(LoyaltyStatus.NONE);

            // Weighted random drink selection
            DrinkType selectedDrink = DrinkType.COLD_BREW;
            double rand = random.nextDouble();
            double cumulative = 0;
            for (DrinkType drink : DrinkType.values()) {
                cumulative += drink.getFrequency();
                if (rand < cumulative) {
                    selectedDrink = drink;
                    break;
                }
            }
            request.setDrinkType(selectedDrink);

            orderService.createOrder(request);
        }
    }

    public boolean isSimulating() {
        return isSimulating;
    }
}
