# kafka-confluent-CDC-project

Please refer to software_design_doc pdf in the repo for detailed report.

## Problem
We need to create an event notification service that sends ̵emails to users about event processing 
results. There are two types of events: success and failure events. Users can choose to subscribe to
either failure events or both success and failure events. The event data is continuously added to a 
MySQL database from customer’s workflow. The event notification service should poll these events from 
database and notify customers via email in near real-time. Each customer's workflow will have its 
own event notification service. The event data stored in the MySQL database contains attributes such
as event_id, status, execution_time, username, error_description, error_code, flow_id, 
and event_source_data.


## Solution overview
The event notification service utilizes Change Data Capture (CDC) for monitoring the MySQL database 
and implementing message queuing for event processing. The service processes success and failure
events, sending email notifications to subscribed users in near real-time, based on their 
preferences and relevant event attributes.

## Proposed Solution
1. Leverage Change Data Capture (CDC) technology to capture changes from the MySQL database in near 
real-time without impacting its performance
2. Utilize an asynchronous message queue to decouple event producers(CDC) and consumers, 
ensuring efficient and scalable event processing
3. Event consumers are designed to categorize events as success or failure based on the queue 
output.
4. The notification microservice functions as the recipient of the message and dispatches event 
status emails to the subscribed customers.


## Tech stack used

1. Java with spring boot framework
2. Apache Kafka on Confluent
3. Debrezium CDC on Confluent
4. MailGun as emailing service (AWS SES can be used)





