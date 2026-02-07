package com.smartbarista.queuesystem.service;

import com.smartbarista.queuesystem.model.CoffeeOrder;
import com.smartbarista.queuesystem.model.Complaint;
import com.smartbarista.queuesystem.model.ComplaintType;
import com.smartbarista.queuesystem.model.CustomerType;
import com.smartbarista.queuesystem.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAllByOrderByTimestampDesc();
    }

    public Complaint saveManualComplaint(String baristaName, String customerName, String message) {
        Complaint complaint = new Complaint(baristaName, null, customerName, ComplaintType.MANUAL, message);
        return complaintRepository.save(complaint);
    }

    public void checkAndReportDelay(CoffeeOrder order) {
        if (order.getArrivalTime() == null)
            return;

        long waitSeconds = Duration.between(order.getArrivalTime(), LocalDateTime.now()).toSeconds();
        long limit = order.getCustomer().getType() == CustomerType.NEW ? 480 : 600; // 8m or 10m

        if (waitSeconds > limit) {
            String baristaName = order.getAssignedBarista() != null ? order.getAssignedBarista().getName()
                    : "Unassigned";
            String reason = order.getStatus().toString().equals("ABANDONED") ? "Order Abandoned after timeout"
                    : "Preparation took too long";

            String message = String.format("Order #%d for %s was delayed. Total time: %dm %ds (Limit: %dm). Reason: %s",
                    order.getId(), order.getCustomer().getName(), waitSeconds / 60, waitSeconds % 60, limit / 60,
                    reason);

            Complaint complaint = new Complaint(
                    baristaName,
                    order.getId(),
                    order.getCustomer().getName(),
                    ComplaintType.DELAYED,
                    message);
            complaintRepository.save(complaint);
        }
    }
}
