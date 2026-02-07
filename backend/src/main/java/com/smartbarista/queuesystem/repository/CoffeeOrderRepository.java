package com.smartbarista.queuesystem.repository;

import com.smartbarista.queuesystem.model.CoffeeOrder;
import com.smartbarista.queuesystem.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByStatusOrderByPriorityScoreDesc(OrderStatus status);

    List<CoffeeOrder> findByStatusOrderByArrivalTimeAsc(OrderStatus status);

    java.util.Optional<CoffeeOrder> findByAssignedBaristaAndStatus(com.smartbarista.queuesystem.model.Barista barista,
            OrderStatus status);

    List<CoffeeOrder> findByStatusInOrderByArrivalTimeDesc(List<OrderStatus> statuses);
}
