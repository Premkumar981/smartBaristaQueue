package com.smartbarista.queuesystem.controller;

import com.smartbarista.queuesystem.model.Complaint;
import com.smartbarista.queuesystem.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public List<Complaint> getComplaints() {
        return complaintService.getAllComplaints();
    }

    @PostMapping("/manual")
    public Complaint raiseManualComplaint(@RequestBody Map<String, String> request) {
        return complaintService.saveManualComplaint(
                request.get("baristaName"),
                request.get("customerName"),
                request.get("message"));
    }
}
