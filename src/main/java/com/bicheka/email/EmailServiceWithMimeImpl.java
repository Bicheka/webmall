package com.bicheka.email;
// package com.bicheka.service.email;

// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import org.springframework.stereotype.Service;

// import jakarta.mail.MessagingException;
// import jakarta.mail.internet.MimeMessage;
// import lombok.AllArgsConstructor;

// @Service
// @AllArgsConstructor
// public class EmailServiceWithMimeImpl implements EmailService {
    
//     private JavaMailSender javaMailSender;
    
//     @Override
//     public void sendEmail(String to, String subject, String text) {

//         // String name = to;
//         // String body = "Hello, %s! \n"+ text;
//         // String formattedMessage = String.format(body, name);

//         String htmlContent = "<html><body style=\"background-color: #f0f0f0;\">" +
//                             "<h1 style=\"color: #444444;\">Welcome to our website!</h1>" +
//                             "<p style=\"color: #666666;\">"+text+"</p>" +
//                             "<p style=\"color: #666666;\">We hope you enjoy your stay.</p>" +
//                             "</body></html>";

//         MimeMessage message = javaMailSender.createMimeMessage();
//         try {
//             MimeMessageHelper helper = new MimeMessageHelper(message, true);
//             helper.setFrom("your_email_address");
//             helper.setTo(to);
//             helper.setSubject(subject);
//             helper.setText(htmlContent, true);
//             javaMailSender.send(message);
//         } catch (MessagingException e) {
//             e.printStackTrace();
//         }
//     }
// }
