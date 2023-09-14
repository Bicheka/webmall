package com.bicheka.s3;


// import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3config {

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public S3Client s3Client(){

        S3Client client = S3Client.builder()
                            .region(Region.of(awsRegion))//this is defined on application.properties             
                            .build();

        return client;
    }
}
