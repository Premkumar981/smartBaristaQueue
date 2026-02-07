package com.smartbarista.queuesystem.service;

import com.smartbarista.queuesystem.model.Barista;
import com.smartbarista.queuesystem.model.CoffeeOrder;
import com.smartbarista.queuesystem.model.OrderStatus;
import com.smartbarista.queuesystem.repository.BaristaRepository;
import com.smartbarista.queuesystem.repository.CoffeeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderSchedulerService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    @Autowired
    private BaristaRepository baristaRepository;

    @Autowired
    private PriorityScoreService priorityScoreService;

    @Autowired
    private FairnessTrackingService fairnessTrackingService;

    @Autowired
    private BaristaService baristaService;

    @Autowired
    private ComplaintService complaintService;

    // Recalculate priorities every 30 seconds as per requirement
    @Scheduled(fixedRate = 30000)
    public void recalculatePriorities() {
        List<CoffeeOrder> waitingOrders = orderRepository.findByStatusOrderByArrivalTimeAsc(OrderStatus.WAITING);
        for (CoffeeOrder order : waitingOrders) {
            priorityScoreService.calculatePriority(order);
            orderRepository.save(order);
        }
        baristaService.updateWorkloadRatios();
    }

    // Try to assign orders to free baristas every 5 seconds for responsiveness
    @Scheduled(fixedRate = 5000)
    public void processQueue() {
        List<Barista> freeBaristas = baristaRepository.findByAvailableTrue();
        if (freeBaristas.isEmpty())
            return;

        List<CoffeeOrder> waitingOrders = orderRepository.findByStatusOrderByPriorityScoreDesc(OrderStatus.WAITING);
        if (waitingOrders.isEmpty())
            return;

        for (Barista barista : freeBaristas) {
            CoffeeOrder bestOrder = null;

            // Workload balancing logic
            if (barista.getCurrentLoad() > 1.2) {
                // Overloaded baristas (>1.2x average) prefer short orders
                bestOrder = waitingOrders.stream()
                        .filter(o -> o.getPrepTime() <= 2)
                        .findFirst()
                        .orElse(null);
            } else if (barista.getCurrentLoad() < 0.8) {
                // Underutilized baristas (<0.8x average) can take complex orders
                bestOrder = waitingOrders.stream()
                        .filter(o -> o.getPrepTime() >= 4)
                        .findFirst()
                        .orElse(null);
            }

            // If no "preferred" order found or load is normal/low, take the highest
            // priority
            if (bestOrder == null && !waitingOrders.isEmpty()) {
                bestOrder = waitingOrders.get(0);
            }

            if (bestOrder != null) {
                assignOrder(bestOrder, barista);
                waitingOrders.remove(bestOrder);
            }
        }
    }

    private void assignOrder(CoffeeOrder order, Barista barista) {
        order.setStatus(OrderStatus.PROCESSING);
        order.setAssignedBarista(barista);
        order.setStartTime(LocalDateTime.now());
        orderRepository.save(order);

        barista.setAvailable(false);
        barista.setTotalWorkTime(barista.getTotalWorkTime() + order.getPrepTime());
        baristaRepository.save(barista);

        fairnessTrackingService.trackFairness(order);
        baristaService.updateWorkloadRatios();
    }

    // Simulate order completion
    @Scheduled(fixedRate = 1000)
    public void simulateCompletion() {
        LocalDateTime now = LocalDateTime.now();

        // 1. Move PROCESSING -> READY
        List<CoffeeOrder> processingOrders = orderRepository.findByStatusOrderByArrivalTimeAsc(OrderStatus.PROCESSING);
        for (CoffeeOrder order : processingOrders) {
            // Check if startTime + prepTime (scaled: 1 min prep = 10s for better demo feel)
            // User requested "timer once order is placed", let's use real minutes but maybe
            // scale for demo.
            // Let's use real minutes as requested "prep time 1 min" etc.
            if (order.getStartTime().plusSeconds(order.getPrepTime() * 10L).isBefore(now)) {
                markAsReady(order);
            }
        }

        // 2. Move READY -> COMPLETED (after 5 seconds)
        List<CoffeeOrder> readyOrders = orderRepository.findByStatusOrderByArrivalTimeAsc(OrderStatus.READY);
        for (CoffeeOrder order : readyOrders) {
            // We'll reuse waitTime or a separate field?
            // Let's just check if it was marked READY more than 5 seconds ago.
            // Since we don't have readyTime, let's use startTime + prepTime + 5s.
            if (order.getStartTime().plusSeconds(order.getPrepTime() * 10L + 5).isBefore(now)) {
                completeOrder(order);
            }
        }
    }

    private void markAsReady(CoffeeOrder order) {
        order.setStatus(OrderStatus.READY);
        orderRepository.save(order);

        Barista barista = order.getAssignedBarista();
        if (barista != null) {
            barista.setAvailable(true);
            barista.setTotalDrinksPrepared(barista.getTotalDrinksPrepared() + 1);
            baristaRepository.save(barista);
        }
    }

    private void completeOrder(CoffeeOrder order) {
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        complaintService.checkAndReportDelay(order);
    }

    // Handle customer abandonment every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void checkAbandonment() {
        List<CoffeeOrder> waitingOrders = orderRepository.findByStatusOrderByArrivalTimeAsc(OrderStatus.WAITING);
        LocalDateTime now = LocalDateTime.now();

        for (CoffeeOrder order : waitingOrders) {
            long waitSeconds = java.time.Duration.between(order.getArrivalTime(), now).toSeconds();
            boolean shouldAbandon = false;

            if (order.getCustomer().getType() == com.smartbarista.queuesystem.model.CustomerType.NEW
                    && waitSeconds >= 480) {
                shouldAbandon = true;
            } else if (order.getCustomer().getType() == com.smartbarista.queuesystem.model.CustomerType.REGULAR
                    && waitSeconds >= 600) {
                shouldAbandon = true;
            }

            if (shouldAbandon) {
                order.setStatus(OrderStatus.ABANDONED);
                order.setPriorityReason("Customer Abandoned (Timeout)");
                orderRepository.save(order);
                complaintService.checkAndReportDelay(order);
            }
        }
    }
}
