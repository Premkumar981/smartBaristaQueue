package com.smartbarista.queuesystem.service;

import com.smartbarista.queuesystem.model.CoffeeOrder;
import com.smartbarista.queuesystem.repository.CoffeeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FairnessTrackingService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public void trackFairness(CoffeeOrder servedOrder) {
        // Find all orders that arrived BEFORE the currently served order but are still
        // waiting
        List<CoffeeOrder> earlierOrders = orderRepository.findByStatusOrderByArrivalTimeAsc(servedOrder.getStatus());

        for (CoffeeOrder earlierOrder : earlierOrders) {
            if (earlierOrder.getArrivalTime().isBefore(servedOrder.getArrivalTime())) {
                Integer count = earlierOrder.getLaterArrivalsServedBefore();
                earlierOrder.setLaterArrivalsServedBefore(count == null ? 1 : count + 1);
                orderRepository.save(earlierOrder);
            }
        }
    }
}
