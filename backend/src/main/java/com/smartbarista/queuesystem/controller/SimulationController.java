package com.smartbarista.queuesystem.controller;

import com.smartbarista.queuesystem.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/simulate")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> start() {
        simulationService.startSimulation();
        return ResponseEntity.ok(Map.of("message", "Simulation started"));
    }

    @PostMapping("/stop")
    public ResponseEntity<Map<String, String>> stop() {
        simulationService.stopSimulation();
        return ResponseEntity.ok(Map.of("message", "Simulation stopped"));
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> status() {
        return ResponseEntity.ok(Map.of("isSimulating", simulationService.isSimulating()));
    }
}
