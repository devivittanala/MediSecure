package com.medsecure.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.medsecure.model.Doctor;
import com.medsecure.repository.DoctorRepository;

@Service
public class CustomDoctorDetailsService
        implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("Doctor Login Attempt = "
                + username);

        Doctor doctor =
                doctorRepository.findByUsername(username);

        System.out.println("Doctor Found = "
                + doctor);

        if (doctor == null) {
            throw new UsernameNotFoundException(
                    "Doctor not found");
        }

        return new User(
                doctor.getUsername(),
                "{noop}" + doctor.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority(
                                "ROLE_DOCTOR"))
        );
    }
}