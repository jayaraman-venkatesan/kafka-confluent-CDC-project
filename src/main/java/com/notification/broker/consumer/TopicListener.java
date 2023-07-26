package com.notification.broker.consumer;
import com.google.gson.Gson;
import com.notification.customer.CustomerService;
import com.notification.email.EmailService;
import com.notification.model.Customer;
import com.notification.model.EmailPayload;
import com.notification.model.EventData;
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

        Gson gson = new Gson();

        EventData eventData = gson.fromJson(payload.value(), EventData.class);

        if (eventData.isDeleted()){
            log.info("Ignore the event as the row is being deleted");
            return;
        }

        SubscribedEventType eventType = SubscribedEventType.fromString(eventData.getStatus());

        List<Customer> customers = this.customerService.getSubscribedCustomersBasedOnEventType(eventType);

        for (Customer customer : customers) {


            Map<String, Object> variablesMap = Map.ofEntries(
                    entry("username", eventData.getUsername()),
                    entry("eventId", eventData.getId()),
                    entry("execution_time",eventData.getExecution_time()),
                    entry("error_code", eventData.getError_code()),
                    entry("error_desc",eventData.getError_desc()),
                    entry("event_source_data",eventData.getEvent_source_data()),
                    entry("flow_id",eventData.getFlow_id())
            );

            String emailBody = templateEngine.process(eventType==SubscribedEventType.Success ? "SuccessEvent.html": "FailureEvent.html", new Context(Locale.getDefault(), variablesMap));
            EmailPayload emailPayload = new EmailPayload();
            emailPayload.toEmailAddress = customer.getEmail();
            emailPayload.fromEmailAddress = eventType==SubscribedEventType.Success ? "Success Event <success-event@gmail.com>": "Failure Event <failure-event@gmail.com>";
            emailPayload.body = emailBody;
            emailPayload.subject = eventType==SubscribedEventType.Success
                    ?"Event Successfully Processed | #["+eventData.getId()+"]"
                    :"Event Processing Failed | #["+eventData.getId()+"]";
            ResponseEntity<String> response = emailService.sendEmail(emailPayload);
            log.info(response.toString());
        }
    }

}