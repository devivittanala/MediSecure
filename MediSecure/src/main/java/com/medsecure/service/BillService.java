package com.medsecure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsecure.model.Bill;
import com.medsecure.repository.BillRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;
    @Autowired
    private NotificationService notificationService;

    public void saveBill(Bill bill) {

        billRepository.save(bill);

        notificationService.addNotification(
                "BILL",
                "Bill generated for "
                + bill.getPatientName());
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    public List<Bill> search(String keyword) {
        return billRepository.findByPatientNameContainingIgnoreCase(keyword);
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }
    public long getBillCount() {
        return billRepository.count();
    }

    public long getPaidBillsCount() {
        return billRepository.findAll()
                .stream()
                .filter(b -> "Paid"
                        .equalsIgnoreCase(b.getPaymentStatus()))
                .count();
    }

    public long getPendingBillsCount() {
        return billRepository.findAll()
                .stream()
                .filter(b -> "Pending"
                        .equalsIgnoreCase(b.getPaymentStatus()))
                .count();
    }

    public double getTotalRevenue() {
        return billRepository.findAll()
                .stream()
                .mapToDouble(Bill::getAmount)
                .sum();
    }
}