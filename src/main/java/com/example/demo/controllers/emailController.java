package com.example.demo.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



@RestController
@CrossOrigin
public class emailController {


     public static class ContactInfoObject {
            public String fullName;
            public String phoneNumber;
            public String emailAddress;
            public String message;

            public ContactInfoObject(String fullName, String phoneNumber, String emailAddress, String message) {
                this.fullName = fullName;
                this.phoneNumber = phoneNumber;
                this.emailAddress = emailAddress;
                this.message = message;
            }
    }

    @PostMapping("/send-message")
    public void sendEmail(@RequestBody ContactInfoObject contactInfo) throws IOException {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(System.getenv("MY_EMAIL"));
        mailSender.setPassword(System.getenv("MY_LOGIN"));


        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(properties);

        String from = "kenjfiddle@gmail.com";
        String to = "kenjsoftware@gmail.com";

        SimpleMailMessage message = new SimpleMailMessage();


        message.setFrom(from);
        message.setTo(to);
        message.setSubject(contactInfo.fullName);
        message.setText(contactInfo.message + "**************" + contactInfo.emailAddress + "***********" + contactInfo.phoneNumber);

        mailSender.send(message);


    }


}
