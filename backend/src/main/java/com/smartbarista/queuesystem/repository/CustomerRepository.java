package com.smartbarista.queuesystem.repository;

import com.smartbarista.queuesystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
