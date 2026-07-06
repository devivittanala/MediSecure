package com.medsecure.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public void sendSms(
            String phone,
            String message) {

        System.out.println(
                "SMS SENT TO : " + phone);

        System.out.println(message);
    }
}