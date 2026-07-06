package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.medsecure.model.Receptionist;
import com.medsecure.service.ReceptionistService;

@Controller
public class AdminReceptionistController {

    @Autowired
    private ReceptionistService receptionistService;

    @GetMapping("/admin/receptionists")
    public String receptionists(Model model) {

        model.addAttribute(
                "receptionists",
                receptionistService.getAllReceptionists());

        return "admin-receptionists";
    }
   
    @PostMapping("/admin/saveReceptionist")
    public String saveReceptionist(
            @ModelAttribute Receptionist receptionist) {

        receptionistService.save(receptionist);

        return "redirect:/admin/receptionists";
    }
    @GetMapping("/admin/addReceptionist")
    public String addReceptionistForm(Model model) {

        model.addAttribute(
                "receptionist",
                new Receptionist());

        return "add-receptionist";
    }
    @GetMapping("/admin/editReceptionist/{id}")
    public String editReceptionist(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "receptionist",
                receptionistService.getById(id));

        return "edit-receptionist";
    }

    @PostMapping("/admin/updateReceptionist")
    public String updateReceptionist(
            @ModelAttribute Receptionist receptionist) {

        receptionistService.save(receptionist);

        return "redirect:/admin/receptionists";
    }
}
