package com.notification.email;

import com.postmarkapp.postmark.*;
import com.postmarkapp.postmark.client.*;

import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService{
    @Override
    public void sendEmail() {
        ApiClient client = Postmark.getApiClient("cd808a61-ec9a-4baf-b285-70ef351055ef");
        Message message = new Message("jayvenkat1998@gmail.com", "clvarsha6@gmail.com", "Hello from Jay!", "Hello message from the most annoying person on the planet!");
        try {
            MessageResponse response = client.deliverMessage(message);
            log.info(response.getMessage());
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
