package com.smartbarista.queuesystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CoffeeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DrinkType drinkType;

    private Integer prepTime; // in minutes

    private LocalDateTime arrivalTime;

    private LocalDateTime startTime;

    private Integer waitTime; // in seconds

    private Double priorityScore;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderStatus status;

    @ManyToOne
    private Barista assignedBarista;

    private String priorityReason;

    private Integer laterArrivalsServedBefore; // For fairness tracking

    public CoffeeOrder() {
    }

    public CoffeeOrder(Long id, Customer customer, DrinkType drinkType, Integer prepTime, LocalDateTime arrivalTime,
            LocalDateTime startTime, Integer waitTime, Double priorityScore, OrderStatus status,
            Barista assignedBarista, String priorityReason, Integer laterArrivalsServedBefore) {
        this.id = id;
        this.customer = customer;
        this.drinkType = drinkType;
        this.prepTime = prepTime;
        this.arrivalTime = arrivalTime;
        this.startTime = startTime;
        this.waitTime = waitTime;
        this.priorityScore = priorityScore;
        this.status = status;
        this.assignedBarista = assignedBarista;
        this.priorityReason = priorityReason;
        this.laterArrivalsServedBefore = laterArrivalsServedBefore;
    }

    public static CoffeeOrderBuilder builder() {
        return new CoffeeOrderBuilder();
    }

    public static class CoffeeOrderBuilder {
        private Long id;
        private Customer customer;
        private DrinkType drinkType;
        private Integer prepTime;
        private LocalDateTime arrivalTime;
        private LocalDateTime startTime;
        private Integer waitTime;
        private Double priorityScore;
        private OrderStatus status;
        private Barista assignedBarista;
        private String priorityReason;
        private Integer laterArrivalsServedBefore;

        public CoffeeOrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CoffeeOrderBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public CoffeeOrderBuilder drinkType(DrinkType drinkType) {
            this.drinkType = drinkType;
            return this;
        }

        public CoffeeOrderBuilder prepTime(Integer prepTime) {
            this.prepTime = prepTime;
            return this;
        }

        public CoffeeOrderBuilder arrivalTime(LocalDateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public CoffeeOrderBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public CoffeeOrderBuilder waitTime(Integer waitTime) {
            this.waitTime = waitTime;
            return this;
        }

        public CoffeeOrderBuilder priorityScore(Double priorityScore) {
            this.priorityScore = priorityScore;
            return this;
        }

        public CoffeeOrderBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public CoffeeOrderBuilder assignedBarista(Barista assignedBarista) {
            this.assignedBarista = assignedBarista;
            return this;
        }

        public CoffeeOrderBuilder priorityReason(String priorityReason) {
            this.priorityReason = priorityReason;
            return this;
        }

        public CoffeeOrderBuilder laterArrivalsServedBefore(Integer laterArrivalsServedBefore) {
            this.laterArrivalsServedBefore = laterArrivalsServedBefore;
            return this;
        }

        public CoffeeOrder build() {
            return new CoffeeOrder(id, customer, drinkType, prepTime, arrivalTime, startTime, waitTime, priorityScore,
                    status, assignedBarista, priorityReason, laterArrivalsServedBefore);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
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

    public Barista getAssignedBarista() {
        return assignedBarista;
    }

    public void setAssignedBarista(Barista assignedBarista) {
        this.assignedBarista = assignedBarista;
    }

    public String getPriorityReason() {
        return priorityReason;
    }

    public void setPriorityReason(String priorityReason) {
        this.priorityReason = priorityReason;
    }

    public Integer getLaterArrivalsServedBefore() {
        return laterArrivalsServedBefore;
    }

    public void setLaterArrivalsServedBefore(Integer laterArrivalsServedBefore) {
        this.laterArrivalsServedBefore = laterArrivalsServedBefore;
    }
}
