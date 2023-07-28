package com.bicheka.email;

import org.springframework.scheduling.annotation.Async;

@Async
public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
