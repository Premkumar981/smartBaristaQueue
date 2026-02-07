package com.smartbarista.queuesystem.controller;

import com.smartbarista.queuesystem.dto.BaristaDTO;
import com.smartbarista.queuesystem.service.BaristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/baristas")
public class BaristaController {

    @Autowired
    private BaristaService baristaService;

    @GetMapping
    public ResponseEntity<List<BaristaDTO>> getAllBaristas() {
        return ResponseEntity.ok(baristaService.getAllBaristas());
    }
}
