package com.smartbarista.queuesystem.service;

import com.smartbarista.queuesystem.model.CoffeeOrder;
import com.smartbarista.queuesystem.model.LoyaltyStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class PriorityScoreService {

    public void calculatePriority(CoffeeOrder order) {
        long waitSeconds = Duration.between(order.getArrivalTime(), LocalDateTime.now()).toSeconds();
        order.setWaitTime((int) waitSeconds);

        // 1. Wait Time (40%) - Max score at 10 minutes (600 seconds)
        double waitScore = Math.min(100.0, (waitSeconds / 600.0) * 100.0) * 0.40;

        // 2. Order Complexity (25%) - Shorter prep time = higher score
        // Max prep time is 6 mins, min is 1 min.
        // Score = (1 - (prepTime - 1) / 5) * 100
        double complexityScore = (1.0 - (order.getPrepTime() - 1.0) / 5.0) * 100.0 * 0.25;

        // 3. Loyalty Status (10%)
        double loyaltyScore = 0;
        if (order.getCustomer().getLoyaltyStatus() == LoyaltyStatus.GOLD) {
            loyaltyScore = 100 * 0.10;
        } else if (order.getCustomer().getLoyaltyStatus() == LoyaltyStatus.SILVER) {
            loyaltyScore = 50 * 0.10;
        }

        // 4. Urgency (25%) - Increases as we approach 10 minutes
        // We start boosting urgency after 5 minutes
        double urgencyScore = 0;
        if (waitSeconds > 300) {
            urgencyScore = Math.min(100.0, ((waitSeconds - 300.0) / 300.0) * 100.0) * 0.25;
        }

        double totalScore = waitScore + complexityScore + loyaltyScore + urgencyScore;

        // Emergency Boost: If wait time > 8 minutes (480s)
        StringBuilder reasoning = new StringBuilder();
        if (waitSeconds > 480) {
            totalScore += 50;
            reasoning.append("Emergency Boost (Wait > 8m); ");
        }

        // Fairness Penalty: if more than 3 later arrivals served first
        if (order.getLaterArrivalsServedBefore() != null && order.getLaterArrivalsServedBefore() > 3) {
            totalScore += 10; // Boost to compensate for being skipped
            reasoning.append("Fairness Adjustment; ");
        }

        if (waitScore > 20)
            reasoning.append("High Wait Time; ");
        if (complexityScore > 15)
            reasoning.append("Quick Order; ");
        if (loyaltyScore > 5)
            reasoning.append("Loyalty Reward; ");
        if (urgencyScore > 10)
            reasoning.append("Approaching Timeout; ");

        order.setPriorityScore(Math.min(200.0, totalScore)); // Capped but Emergency can push it high
        order.setPriorityReason(reasoning.toString());
    }
}
