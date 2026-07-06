package com.medsecure.config;

import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

    private final String ACCOUNT_SID =
            "YOUR_ACCOUNT_SID";

    private final String AUTH_TOKEN =
            "YOUR_AUTH_TOKEN";

    @PostConstruct
    public void init() {

        Twilio.init(
                ACCOUNT_SID,
                AUTH_TOKEN
        );
    }
}