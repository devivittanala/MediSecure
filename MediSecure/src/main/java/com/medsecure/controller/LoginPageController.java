package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medsecure.repository.DoctorRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginPageController {
	 @Autowired
	    private DoctorRepository doctorRepository;

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin-login";
    }

   

    @GetMapping("/reception/login")
    public String receptionLogin() {
        return "reception-login";
    }
    @PostMapping("/admin/login")
    public String adminLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {

        if(username.equals("admin")
                && password.equals("admin123")) {

            session.setAttribute(
                    "adminUsername",
                    username);

            return "redirect:/dashboard";
        }

        return "redirect:/admin/login?error=true";
    }
    @PostMapping("/reception/login")
    public String receptionLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {

        if(username.equals("reception")
                && password.equals("recep123")) {

            session.setAttribute(
                    "receptionUsername",
                    username);

            return "redirect:/reception/dashboard";
        }

        return "redirect:/reception/login?error=true";
    }


}