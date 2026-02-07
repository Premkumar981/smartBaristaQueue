package com.smartbarista.queuesystem.dto;

import com.smartbarista.queuesystem.model.CustomerType;
import com.smartbarista.queuesystem.model.DrinkType;
import com.smartbarista.queuesystem.model.LoyaltyStatus;

public class OrderRequestDTO {
    private String customerName;
    private CustomerType customerType;
    private LoyaltyStatus loyaltyStatus;
    private DrinkType drinkType;

    public OrderRequestDTO() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public LoyaltyStatus getLoyaltyStatus() {
        return loyaltyStatus;
    }

    public void setLoyaltyStatus(LoyaltyStatus loyaltyStatus) {
        this.loyaltyStatus = loyaltyStatus;
    }

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(DrinkType drinkType) {
        this.drinkType = drinkType;
    }
}
