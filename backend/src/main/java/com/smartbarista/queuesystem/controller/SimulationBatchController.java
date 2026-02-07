package com.smartbarista.queuesystem.controller;

import com.smartbarista.queuesystem.dto.SimulationResultDTO;
import com.smartbarista.queuesystem.service.SimulationBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulation/batch")
@CrossOrigin(origins = "*")
public class SimulationBatchController {

    @Autowired
    private SimulationBatchService simulationBatchService;

    @GetMapping("/run")
    public List<SimulationResultDTO> runBatch(@RequestParam(defaultValue = "1") int testCases) {
        return simulationBatchService.runMultipleTestCases(testCases);
    }
}
