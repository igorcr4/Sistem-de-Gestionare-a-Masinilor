package Sistem.de.Gestionare.a.Masinilor.jpa.services.impl;

import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.EmailSendingException;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void mailSender(String to, String subject, StringBuilder body) throws RuntimeException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body.toString());

        try {
            mailSender.send(message);
        }catch (MailException ex) {
            throw new EmailSendingException("Failed to send email: " + ex.getMessage());
        }
    }
}
