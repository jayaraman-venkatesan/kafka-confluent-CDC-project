package com.notification.email;
import com.notification.model.EmailPayload;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<String> sendEmail(EmailPayload emailPayload);
}
