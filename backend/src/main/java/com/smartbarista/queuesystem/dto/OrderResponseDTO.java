package com.smartbarista.queuesystem.dto;

import com.smartbarista.queuesystem.model.DrinkType;
import com.smartbarista.queuesystem.model.OrderStatus;
import java.time.LocalDateTime;

public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private String customerType;
    private String loyaltyStatus;
    private DrinkType drinkType;
    private Integer prepTime;
    private LocalDateTime arrivalTime;
    private Integer waitTime;
    private Integer estimatedWaitTime;
    private Double priorityScore;
    private OrderStatus status;
    private String assignedBaristaName;
    private String priorityReason;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Long id, String customerName, String customerType, String loyaltyStatus,
            DrinkType drinkType, Integer prepTime, LocalDateTime arrivalTime, Integer waitTime,
            Integer estimatedWaitTime, Double priorityScore, OrderStatus status, String assignedBaristaName,
            String priorityReason) {
        this.id = id;
        this.customerName = customerName;
        this.customerType = customerType;
        this.loyaltyStatus = loyaltyStatus;
        this.drinkType = drinkType;
        this.prepTime = prepTime;
        this.arrivalTime = arrivalTime;
        this.waitTime = waitTime;
        this.estimatedWaitTime = estimatedWaitTime;
        this.priorityScore = priorityScore;
        this.status = status;
        this.assignedBaristaName = assignedBaristaName;
        this.priorityReason = priorityReason;
    }

    public static OrderResponseDTOBuilder builder() {
        return new OrderResponseDTOBuilder();
    }

    public static class OrderResponseDTOBuilder {
        private Long id;
        private String customerName;
        private String customerType;
        private String loyaltyStatus;
        private DrinkType drinkType;
        private Integer prepTime;
        private LocalDateTime arrivalTime;
        private Integer waitTime;
        private Integer estimatedWaitTime;
        private Double priorityScore;
        private OrderStatus status;
        private String assignedBaristaName;
        private String priorityReason;

        public OrderResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderResponseDTOBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public OrderResponseDTOBuilder customerType(String customerType) {
            this.customerType = customerType;
            return this;
        }

        public OrderResponseDTOBuilder loyaltyStatus(String loyaltyStatus) {
            this.loyaltyStatus = loyaltyStatus;
            return this;
        }

        public OrderResponseDTOBuilder drinkType(DrinkType drinkType) {
            this.drinkType = drinkType;
            return this;
        }

        public OrderResponseDTOBuilder prepTime(Integer prepTime) {
            this.prepTime = prepTime;
            return this;
        }

        public OrderResponseDTOBuilder arrivalTime(LocalDateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public OrderResponseDTOBuilder waitTime(Integer waitTime) {
            this.waitTime = waitTime;
            return this;
        }

        public OrderResponseDTOBuilder estimatedWaitTime(Integer estimatedWaitTime) {
            this.estimatedWaitTime = estimatedWaitTime;
            return this;
        }

        public OrderResponseDTOBuilder priorityScore(Double priorityScore) {
            this.priorityScore = priorityScore;
            return this;
        }

        public OrderResponseDTOBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderResponseDTOBuilder assignedBaristaName(String assignedBaristaName) {
            this.assignedBaristaName = assignedBaristaName;
            return this;
        }

        public OrderResponseDTOBuilder priorityReason(String priorityReason) {
            this.priorityReason = priorityReason;
            return this;
        }

        public OrderResponseDTO build() {
            return new OrderResponseDTO(id, customerName, customerType, loyaltyStatus, drinkType, prepTime, arrivalTime,
                    waitTime, estimatedWaitTime, priorityScore, status, assignedBaristaName, priorityReason);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getLoyaltyStatus() {
        return loyaltyStatus;
    }

    public void setLoyaltyStatus(String loyaltyStatus) {
        this.loyaltyStatus = loyaltyStatus;
    }

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(DrinkType drinkType) {
        this.drinkType = drinkType;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public Integer getEstimatedWaitTime() {
        return estimatedWaitTime;
    }

    public void setEstimatedWaitTime(Integer estimatedWaitTime) {
        this.estimatedWaitTime = estimatedWaitTime;
    }

    public Double getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(Double priorityScore) {
        this.priorityScore = priorityScore;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getAssignedBaristaName() {
        return assignedBaristaName;
    }

    public void setAssignedBaristaName(String assignedBaristaName) {
        this.assignedBaristaName = assignedBaristaName;
    }

    public String getPriorityReason() {
        return priorityReason;
    }

    public void setPriorityReason(String priorityReason) {
        this.priorityReason = priorityReason;
    }
}
