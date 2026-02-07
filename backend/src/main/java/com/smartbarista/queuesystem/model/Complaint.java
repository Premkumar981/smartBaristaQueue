package com.smartbarista.queuesystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baristaName;
    private Long orderId;
    private String customerName;

    @Enumerated(EnumType.STRING)
    private ComplaintType type;

    @Column(length = 1000)
    private String message;

    private LocalDateTime timestamp;

    public Complaint() {
    }

    public Complaint(String baristaName, Long orderId, String customerName, ComplaintType type, String message) {
        this.baristaName = baristaName;
        this.orderId = orderId;
        this.customerName = customerName;
        this.type = type;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaristaName() {
        return baristaName;
    }

    public void setBaristaName(String baristaName) {
        this.baristaName = baristaName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ComplaintType getType() {
        return type;
    }

    public void setType(ComplaintType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
