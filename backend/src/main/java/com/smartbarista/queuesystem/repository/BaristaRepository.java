package com.smartbarista.queuesystem.repository;

import com.smartbarista.queuesystem.model.Barista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaristaRepository extends JpaRepository<Barista, Long> {
    List<Barista> findByAvailableTrue();
}
