package com.smartbarista.queuesystem.service;

import com.smartbarista.queuesystem.dto.OrderRequestDTO;
import com.smartbarista.queuesystem.dto.OrderResponseDTO;
import com.smartbarista.queuesystem.model.*;
import com.smartbarista.queuesystem.repository.CoffeeOrderRepository;
import com.smartbarista.queuesystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PriorityScoreService priorityScoreService;

    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Customer customer = Customer.builder()
                .name(request.getCustomerName())
                .type(request.getCustomerType())
                .loyaltyStatus(request.getLoyaltyStatus())
                .build();
        Customer savedCustomer = customerRepository.save(customer);
        if (savedCustomer == null)
            throw new RuntimeException("Failed to save customer");
        customer = savedCustomer;

        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .drinkType(request.getDrinkType())
                .prepTime(request.getDrinkType().getPrepTime())
                .arrivalTime(LocalDateTime.now())
                .status(OrderStatus.WAITING)
                .priorityScore(0.0)
                .laterArrivalsServedBefore(0)
                .build();

        priorityScoreService.calculatePriority(order);
        order = orderRepository.save(order);

        return mapToDTO(order);
    }

    public List<OrderResponseDTO> getQueue() {
        List<CoffeeOrder> waitingOrders = orderRepository.findByStatusOrderByPriorityScoreDesc(OrderStatus.WAITING);

        int cumulativePrepTime = 0;
        List<OrderResponseDTO> response = new java.util.ArrayList<>();

        for (CoffeeOrder order : waitingOrders) {
            cumulativePrepTime += order.getPrepTime();
            OrderResponseDTO dto = mapToDTO(order);
            // Rough estimate: cumulative prep time divided by 3 baristas
            dto.setEstimatedWaitTime(cumulativePrepTime / 3);
            response.add(dto);
        }

        return response;
    }

    public List<OrderResponseDTO> getAllOrders() {
        // Return active orders and last 20 completed/abandoned
        List<CoffeeOrder> active = orderRepository.findByStatusInOrderByArrivalTimeDesc(
                List.of(OrderStatus.WAITING, OrderStatus.PROCESSING, OrderStatus.READY));
        List<CoffeeOrder> history = orderRepository.findByStatusInOrderByArrivalTimeDesc(
                List.of(OrderStatus.COMPLETED, OrderStatus.ABANDONED));

        List<OrderResponseDTO> response = new java.util.ArrayList<>();
        active.forEach(o -> response.add(mapToDTO(o)));
        history.stream().limit(20).forEach(o -> response.add(mapToDTO(o)));

        return response;
    }

    public OrderResponseDTO mapToDTO(CoffeeOrder order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerName(order.getCustomer().getName())
                .customerType(order.getCustomer().getType().name())
                .loyaltyStatus(order.getCustomer().getLoyaltyStatus().name())
                .drinkType(order.getDrinkType())
                .prepTime(order.getPrepTime())
                .arrivalTime(order.getArrivalTime())
                .waitTime(order.getWaitTime())
                .priorityScore(order.getPriorityScore())
                .status(order.getStatus())
                .assignedBaristaName(order.getAssignedBarista() != null ? order.getAssignedBarista().getName() : null)
                .priorityReason(order.getPriorityReason())
                .build();
    }
}
