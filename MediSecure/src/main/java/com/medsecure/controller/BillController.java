package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.medsecure.model.Bill;
import com.medsecure.service.BillService;
import com.medsecure.repository.BillRepository;
@Controller
public class BillController {
	@Autowired
	private BillRepository billRepository;
    @Autowired
    private BillService billService;

    @GetMapping("/addBill")
    public String addBillForm(Model model) {

        model.addAttribute("bill", new Bill());

        return "add-bill";
    }

    @PostMapping("/saveBill")
    public String saveBill(@ModelAttribute Bill bill) {

        billService.saveBill(bill);

        return "redirect:/bills";
    }

    @GetMapping("/bills")
    public String getBills(Model model) {

        model.addAttribute("bills", billService.getAllBills());

        model.addAttribute("billCount", billService.getBillCount());

        model.addAttribute("paidBills", billService.getPaidBillsCount());

        model.addAttribute("pendingBills", billService.getPendingBillsCount());

        model.addAttribute("totalRevenue", billService.getTotalRevenue());

        model.addAttribute("dashboardUrl", "/dashboard");

        return "bills";
    }
    
   

    @GetMapping("/searchBills")
    public String searchBills(
            @RequestParam String keyword,
            Model model) {

        model.addAttribute(
                "bills",
                billService.search(keyword));

        return "bills";
    }
    @GetMapping("/reception/bill/{id}")
    public String receptionBillProfile(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "bill",
                billService.getBillById(id));

        model.addAttribute(
                "backUrl",
                "/reception/bills");

        return "bill-profile";
    }
    @GetMapping("/reception/bills")
    public String receptionBills(Model model) {

        model.addAttribute(
                "bills",
                billService.getAllBills());

        model.addAttribute(
                "billCount",
                billService.getBillCount());

        model.addAttribute(
                "paidBills",
                billService.getPaidBillsCount());

        model.addAttribute(
                "pendingBills",
                billService.getPendingBillsCount());

        return "reception-bills";
    }
    @GetMapping("/bill/{id}")
    public String billProfile(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "bill",
                billService.getBillById(id));

        model.addAttribute(
                "backUrl",
                "/bills");

        return "bill-profile";
    }

    @GetMapping("/deleteBill/{id}")
    public String deleteBill(@PathVariable Long id) {

        billService.deleteBill(id);

        return "redirect:/bills";
    }
    @GetMapping("/editBill/{id}")
    public String editBill(
            @PathVariable Long id,
            Model model) {

        Bill bill = billService.getBillById(id);

        model.addAttribute("bill", bill);

        return "edit-bill";
    }

    @PostMapping("/updateBill")
    public String updateBill(
            @ModelAttribute Bill bill) {

        billService.saveBill(bill);

        return "redirect:/bills";
    }
}