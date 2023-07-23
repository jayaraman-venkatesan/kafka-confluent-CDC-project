package com.notification.broker.consumer;

import com.notification.customer.CustomerService;
import com.notification.email.EmailService;
import com.notification.model.Customer;
import com.notification.model.EmailPayload;
import com.notification.model.SubscribedEventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

    @Autowired
    private final EmailService emailService;

    @Autowired
    private final CustomerService customerService;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload) {
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());
        SubscribedEventType eventType = SubscribedEventType.Success;

        List<Customer> customers = this.customerService.getSubscribedCustomersBasedOnEventType(eventType);

        for (Customer customer : customers) {
            EmailPayload emailPayload = new EmailPayload();
            emailPayload.toEmailAddress = customer.getEmail();
            // TODO update from email address to be dynamic
            emailPayload.fromEmailAddress = "jayvenkat1998@gmail.com";
            emailPayload.body = "Hello ${eventType}";
            emailPayload.subject = "Hello ${subject}";
            emailService.sendEmail(emailPayload);
        }
    }

}