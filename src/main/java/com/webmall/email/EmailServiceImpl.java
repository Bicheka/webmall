package com.webmall.email;

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

       
        //TODO: change the url to the url of your frontend, and then the frontend will send a post request to the confirm_email endpoint
        String confirmationLink = UriComponentsBuilder.fromUriString("http://localhost:8080/confirm_email")
        .queryParam("email", to)
        .build().toUriString();
        text += "\n\nPlease click on the following link to confirm your email address: " + confirmationLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(System.getenv("SPRING_MAIL_USERNAME"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        
        try {
            emailSender.send(message);
            System.out.println("Email sent successfully...");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Email not sent");
        }
    }
    
}
