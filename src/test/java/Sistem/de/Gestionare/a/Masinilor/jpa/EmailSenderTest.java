package Sistem.de.Gestionare.a.Masinilor.jpa;

import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.EmailSendingException;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailSenderTest {
    @Autowired
    private EmailSendingException exception;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    public void testMailSender_Success() {
        String to = "test@gmail.com";
        String subject = "test subject";
        StringBuilder body = new StringBuilder("test body");

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.mailSender(to, subject, body);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals(to,sentMessage.getTo()[0]);
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(body.toString(), sentMessage.getText());
    }

    @Test
    public void testMailSender_Failure() {
        String to = "test@gmail.com";
        String subject = "test subject";
        StringBuilder body = new StringBuilder("test body");

        doThrow(new EmailSendingException("Error!")).when(mailSender).send(any(SimpleMailMessage.class));

        assertThrows(EmailSendingException.class, () -> emailService.mailSender(to, subject, body));
    }
}
