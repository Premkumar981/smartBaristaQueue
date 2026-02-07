package com.smartbarista.queuesystem.model;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CustomerType type;

    @Enumerated(EnumType.STRING)
    private LoyaltyStatus loyaltyStatus;

    public Customer() {
    }

    public Customer(Long id, String name, CustomerType type, LoyaltyStatus loyaltyStatus) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.loyaltyStatus = loyaltyStatus;
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public static class CustomerBuilder {
        private Long id;
        private String name;
        private CustomerType type;
        private LoyaltyStatus loyaltyStatus;

        public CustomerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder type(CustomerType type) {
            this.type = type;
            return this;
        }

        public CustomerBuilder loyaltyStatus(LoyaltyStatus loyaltyStatus) {
            this.loyaltyStatus = loyaltyStatus;
            return this;
        }

        public Customer build() {
            return new Customer(id, name, type, loyaltyStatus);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public LoyaltyStatus getLoyaltyStatus() {
        return loyaltyStatus;
    }

    public void setLoyaltyStatus(LoyaltyStatus loyaltyStatus) {
        this.loyaltyStatus = loyaltyStatus;
    }
}
