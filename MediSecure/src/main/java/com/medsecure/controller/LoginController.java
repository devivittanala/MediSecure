package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.medsecure.model.Doctor;
import com.medsecure.model.User;
import com.medsecure.repository.DoctorRepository;
import com.medsecure.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    
    

        @GetMapping("/login")
        public String loginPage() {
            return "login";
        }
    


}