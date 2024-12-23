package Sistem.de.Gestionare.a.Masinilor.jpa.services;

public interface EmailService {
    void mailSender(String to, String subject, StringBuilder body);
}
