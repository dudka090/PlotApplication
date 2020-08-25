package pl.myapplication.plot.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    private final JavaMailSender javaMailSender = new JavaMailSenderImpl();

    public void sendEmail(String to, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("plot.application@op.pl");
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        javaMailSender.send(message);
        new ResponseEntity<>("Wiadomość została wysłana", HttpStatus.OK);
    }
    }

