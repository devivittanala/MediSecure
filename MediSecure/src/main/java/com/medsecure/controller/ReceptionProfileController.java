package com.medsecure.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ReceptionProfileController {

	@GetMapping("/reception/profile")
	public String profile(Model model) {

	    model.addAttribute("name", "Receptionist");
	    model.addAttribute("role", "Front Desk Executive");

	    model.addAttribute(
	            "photoPath",
	            "/images/default-user.png");

	    return "reception-profile";
	}

    
    @GetMapping("/reception/editProfile")
    public String editProfile(Model model) {

        model.addAttribute("name", "Receptionist");
        model.addAttribute("employeeId", "REC001");
        model.addAttribute("email", "reception@medsecure.com");
        model.addAttribute("phone", "9876543210");
        model.addAttribute("department", "Reception");
        model.addAttribute("shiftTiming", "09:00 AM - 06:00 PM");
        model.addAttribute("address", "MediSecure Hospital");

        return "edit-reception-profile";
    }
    @PostMapping("/reception/updateProfile")
    public String updateProfile() {

        // save data later

        return "redirect:/reception/profile";
    }
    @PostMapping("/reception/uploadPhoto")
    public String uploadPhoto(
            @RequestParam("photo") MultipartFile photo)
            throws IOException {

        String uploadDir =
                System.getProperty("user.home")
                + "/Pictures/MediSecure/";

        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName =
                photo.getOriginalFilename();

        File destination =
                new File(uploadDir + fileName);

        photo.transferTo(destination);

        System.out.println(
                "Photo saved at: "
                + destination.getAbsolutePath());

        return "redirect:/reception/profile";
    }

}