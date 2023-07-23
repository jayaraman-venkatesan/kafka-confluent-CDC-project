package com.notification.email;

import com.notification.model.EmailPayload;

public interface EmailService {
    void sendEmail(EmailPayload emailPayload);
}
