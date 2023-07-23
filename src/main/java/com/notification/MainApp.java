package com.notification;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.notification.config.AwsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Slf4j
@SpringBootApplication
public class MainApp {

    @Autowired
    AwsConfig awsConfig;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApp.class, args);
    }

    private AWSCredentialsProvider getCredentialsProvider() {
        if (!awsConfig.awsAccessKeyId.isEmpty() && !awsConfig.awsSecretKey.isEmpty()) {
            log.info("Using AWS Access Key provided");
            return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsConfig.awsAccessKeyId, awsConfig.awsSecretKey));
        } else {
            log.info("Using Default Provider Chain");
            return new DefaultAWSCredentialsProviderChain();
        }
    }

    private Regions getAwsRegion() {
        return !awsConfig.awsRegion.isEmpty() ?
                Regions.fromName(awsConfig.awsRegion) :
                Regions.US_EAST_1;
    }

    @Bean
    AmazonSimpleEmailService amazonSimpleEmailService() {
        return AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withRegion(getAwsRegion())
                .withCredentials(getCredentialsProvider())
                .build();
    }

}
