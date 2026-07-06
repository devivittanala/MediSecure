package com.medsecure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.medsecure.model.Appointment;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAppointmentMail(
            String email,
            Appointment appointment) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(email);

        mail.setSubject("MediSecure Appointment Confirmation");

        mail.setText(

                "Dear " + appointment.getPatientName() +

                "\n\nYour appointment has been booked successfully."

                + "\n\nAppointment ID : " + appointment.getAppointmentId()

                + "\nOTP : " + appointment.getOtp()

                + "\nDoctor : " + appointment.getDoctorName()

                + "\nDate : " + appointment.getAppointmentDate()

                + "\nTime : " + appointment.getAppointmentTime()

                + "\n\nThank You"

                + "\nMediSecure Hospital"

        );

        mailSender.send(mail);

    }

}