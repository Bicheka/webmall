package com.bicheka.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{

    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {

        String confirmationLink = UriComponentsBuilder.fromUriString("localhost:8080/confirm")
        .queryParam("email", to)
        .build().toUriString();
        text += "\n\nPlease click on the following link to confirm your email address: " + confirmationLink;
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bichekacoder@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        System.out.println("Email sent successfully...");
    }
    
}
