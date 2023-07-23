package com.notification.email;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.notification.model.EmailPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    @Autowired
    AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public void sendEmail(EmailPayload emailPayload) {
        try {
            SendEmailRequest sendEmailRequest = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(emailPayload.toEmailAddress)
                    )
                    .withMessage(
                            new Message()
                                    .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(emailPayload.body)))
                                    .withSubject(new Content().withCharset("UTF-8").withData(emailPayload.subject))
                    )
                    .withSource(emailPayload.fromEmailAddress);

            amazonSimpleEmailService.sendEmail(sendEmailRequest);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
