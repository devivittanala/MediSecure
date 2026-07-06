package com.medsecure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomDoctorDetailsService customDoctorDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        UserDetails receptionist = User.withDefaultPasswordEncoder()
                .username("reception")
                .password("recep123")
                .roles("RECEPTIONIST")
                .build();

        return new InMemoryUserDetailsManager(
                admin,
                receptionist
        );
    }

    @Bean
    public AuthenticationProvider doctorAuthenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                customDoctorDetailsService);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

            		.requestMatchers(
            		        "/",
            		        "/admin/login",
            		        "/doctor/login",
            		        "/reception/login",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/book",
                        "/bookAppointment",
                        "/track-appointment",
                        "/trackAppointment")
                .permitAll()

                .requestMatchers("/dashboard").permitAll()
                .requestMatchers("/reception/**").permitAll()
                .requestMatchers("/doctor/**").permitAll()
                .requestMatchers(
                        "/addAppointment",
                        "/saveAppointment",
                        "/editAppointment/**",
                        "/deleteAppointment/**",
                        "/checkInPatient/**",
                        "/appointments",
                        "/appointments/**")
                .permitAll()

                .requestMatchers(
                        "/patients",
                        "/patients/**",
                        "/searchPatients",
                        "/addPatient",
                        "/savePatient",
                        "/editPatient/**",
                        "/deletePatient/**",
                        "/patient/**",
                        "/patients/pdf",
                        "/patients/excel",
                        "/doctors",
                        "/doctors/**",
                        "/searchDoctors",
                        "/searchDoctor",
                        
                       
                        "/addDoctor",
                        "/saveDoctor",
                        "/editDoctor/**",
                        "/deleteDoctor/**",
                       
                        "/bills",
                        "/bills/**",
                        "/bill/**",
                        "/addBill",
                        "/saveBill",
                        "/editBill/**",
                        "/updateBill",
                        "/deleteBill/**",
                        "/searchBills",
                        "/reports",
                        "/reports/**",
                        "/analytics",
                        "/analytics/**",
                        "/notifications",
                        "/notifications/**",
                        "/settings",
                        "/settings/**",
                        "/activity-logs",
                        "/activity-logs/**",
                        "/appointments",
                        "/appointments/**",
                        "/searchAppointments",
                        "/addAppointment",
                        "/doctor/todayAppointments",
                       
                        "/saveAppointment",
                        "/editAppointment/**",
                        "/updateAppointmentReception",
                        "/deleteAppointment/**",
                        "/checkInPatient/**",
                        "/callNextPatient",
                        "/completePatient/**",
                        "/appointment/**",
                        "/reception/bills",
                        "/reception/bill/**",
                        "/reception/searchBills",
                        "/reception/profile",
                        "/reception/editProfile",
                        "/reception/updateProfile",
                        "/reception/uploadPhoto",
                        "/admin/receptionists",
                        "/admin/receptionists/**",
                        "/admin/editReceptionist/**",
                        "/sendToDoctor/**",
                        "/doctor/complete/**",
                        
                       
                        "/admin/addReceptionist",
                        "/admin/saveReceptionist",
                        
                        "/admin/updateReceptionist",
                        "/admin/deleteReceptionist/**",
                        "/",
                        "/bookAppointment",
                        "/trackAppointment",
                        
                        "/css/**",
                        "/js/**",
                        "/images/**")
                       

                .permitAll()
                .anyRequest()
                
                
                .authenticated()
            )

            

            .logout(logout -> logout.permitAll());

        return http.build();
    }
}