package com.notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsConfig {

    @Value("${aws.accessKeyId}")
    public String awsAccessKeyId;

    @Value("${aws.secretKey}")
    public String awsSecretKey;

    @Value("${aws.region}")
    public String awsRegion;
}
