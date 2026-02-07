package com.smartbarista.queuesystem.controller;

import com.smartbarista.queuesystem.dto.OrderRequestDTO;
import com.smartbarista.queuesystem.dto.OrderResponseDTO;
import com.smartbarista.queuesystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/queue")
    public ResponseEntity<List<OrderResponseDTO>> getQueue() {
        return ResponseEntity.ok(orderService.getQueue());
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
