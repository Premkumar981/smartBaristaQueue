package com.smartbarista.queuesystem.service;

import com.smartbarista.queuesystem.dto.BaristaDTO;
import com.smartbarista.queuesystem.model.Barista;
import com.smartbarista.queuesystem.repository.BaristaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaristaService {

    @Autowired
    private com.smartbarista.queuesystem.repository.CoffeeOrderRepository orderRepository;

    @Autowired
    private BaristaRepository baristaRepository;

    @PostConstruct
    public void init() {
        if (baristaRepository.count() == 0) {
            baristaRepository.save(
                    Barista.builder().name("Barista A").currentLoad(1.0).totalWorkTime(0).available(true).build());
            baristaRepository.save(
                    Barista.builder().name("Barista B").currentLoad(1.0).totalWorkTime(0).available(true).build());
            baristaRepository.save(
                    Barista.builder().name("Barista C").currentLoad(1.0).totalWorkTime(0).available(true).build());
        }
    }

    public List<BaristaDTO> getAllBaristas() {
        List<Barista> baristas = baristaRepository.findAll();
        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        return baristas.stream()
                .map(b -> {
                    BaristaDTO dto = BaristaDTO.builder()
                            .id(b.getId())
                            .name(b.getName())
                            .currentLoad(b.getCurrentLoad())
                            .totalWorkTime(b.getTotalWorkTime())
                            .totalDrinksPrepared(b.getTotalDrinksPrepared())
                            .available(b.isAvailable())
                            .build();

                    // Find active order
                    orderRepository
                            .findByAssignedBaristaAndStatus(b,
                                    com.smartbarista.queuesystem.model.OrderStatus.PROCESSING)
                            .ifPresent(order -> {
                                dto.setCurrentDrink(order.getDrinkType());
                                long secondsElapsed = java.time.Duration.between(order.getStartTime(), now).toSeconds();
                                long totalSeconds = order.getPrepTime() * 10L;
                                dto.setRemainingPrepSeconds((int) Math.max(0, totalSeconds - secondsElapsed));
                            });

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void updateWorkloadRatios() {
        List<Barista> baristas = baristaRepository.findAll();
        double maxWork = baristas.stream().mapToDouble(Barista::getTotalWorkTime).max().orElse(0.0);

        if (maxWork < 1.0) {
            baristas.forEach(b -> b.setCurrentLoad(1.0));
        } else {
            baristas.forEach(b -> b.setCurrentLoad(b.getTotalWorkTime() / maxWork));
        }
        baristaRepository.saveAll(baristas);
    }
}
