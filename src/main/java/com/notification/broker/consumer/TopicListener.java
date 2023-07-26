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
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;

import org.thymeleaf.context.Context;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Map.entry;


@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

    @Autowired
    private final EmailService emailService;

    @Autowired
    private final CustomerService customerService;

    @Autowired
    private final ITemplateEngine templateEngine;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload) {
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());
        SubscribedEventType eventType = SubscribedEventType.Success;

        List<Customer> customers = this.customerService.getSubscribedCustomersBasedOnEventType(eventType);

        for (Customer customer : customers) {


            Map<String, Object> variablesMap = Map.ofEntries(
                    entry("username", "Varsha"),
                    entry("eventId", "1"),
                    entry("execution_time",1),
                    //TODO error not needed for success events
                    entry("error_code","AX002"),
                    entry("error_desc","It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)."),
                    entry("event_source_data","Order Placed"),
                    entry("flow_id","OD1")
            );

//            String emailBody = templateEngine.process("SuccessEvent.html", new Context(Locale.getDefault(), variablesMap));
            String emailBody = templateEngine.process("FailureEvent.html", new Context(Locale.getDefault(), variablesMap));
            EmailPayload emailPayload = new EmailPayload();
            emailPayload.toEmailAddress = customer.getEmail();
            // TODO update from email address to be dynamic
            emailPayload.fromEmailAddress = "Success Event <success-event@gmail.com>";
            emailPayload.body = emailBody;
            emailPayload.subject = "["+"event_source_data"+"] - Event Successfully Processed";

            ResponseEntity<String> response = emailService.sendEmail(emailPayload);
            log.info(response.toString());
        }
    }

}